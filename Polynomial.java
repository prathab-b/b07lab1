import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial
{
    double[] coefficients = new double[1024];
    int [] exponents = new int[1024];

    public Polynomial()
    {
        coefficients = new double[] {0};
        exponents = new int[] {1};
    }

    public Polynomial(double[] coefficients_to_add, int[] exponents_to_add)
    {
        int count = 0;
        
        for (int i = 0; i < coefficients_to_add.length; i++)
        {
            coefficients[i] = coefficients_to_add[i];
            exponents[i] = exponents_to_add[i];
            count++;
        }        
    }

    public Polynomial(File file) throws FileNotFoundException
    {
        Scanner input = new Scanner(file);
        String poly = input.nextLine();

        input.close();

        String[] terms = new String[1024];
        String[] split_one = poly.split("\\+");
        int term_count = 0;
        int coefficients_count = 0;

        for (int i = 0; i < split_one.length; i++)
        {
            String[] split_two = split_one[i].split("\\-");

            if (split_two.length == 0)
            {
                break;
            }

            if (split_two.length == 1)
            {
                terms[term_count] = split_two[0];
                term_count++;
            }

            else
            {
                if (split_two[0].equals(""))
                {
                    terms[term_count] = "-" + split_two[1];
                    term_count++;
                }

                else
                {
                    terms[term_count] = split_two[0];
                    term_count++;
                    terms[term_count] = "-" + split_two[1];
                    term_count++;
                }

                if (split_two.length > 2)
                {
                    for (int j = 2; j < split_two.length; j++)
                    {
                        terms[term_count] = "-" + split_two[j];
                        term_count++;
                    }
                }
            }
        }

        for (int i = 0; i < term_count; i++)
        {
            String[] split_three = terms[i].split("x");

            if (split_three.length == 1)
            {
                coefficients[coefficients_count] = Double.parseDouble(split_three[0]);
                exponents[coefficients_count] = 0;
                coefficients_count++;
            }

            else if (split_three.length == 2)
            {
                coefficients[coefficients_count] = Double.parseDouble(split_three[0]);
                exponents[coefficients_count] = Integer.parseInt(split_three[1]);
                coefficients_count++;
            }
        }
    }

    public Polynomial add(Polynomial poly)
    {
        int length = this.coefficients.length + poly.coefficients.length;
        double[] new_coefficients = new double[length];
        int[] new_exponents = new int[length];
        int count = 0;
        int count_two = 0;
        int count_three = 0;
        boolean flag = false;

        for (int i = 0; i < this.coefficients.length; i++)
        {
            new_exponents[i] = this.exponents[i];
            new_coefficients[i] = this.coefficients[i];
            count++;
        }

        for (int i = 0; i < poly.coefficients.length; i++)
        {                    
            flag = false;
            
            for (int j = 0; j < this.coefficients.length; j++)
            {
                if (poly.exponents[i] == new_exponents[j])
                {
                    new_coefficients[j] += poly.coefficients[i];
                    flag = true;
                    break;
                }
            }

            if (flag == false)
            {
                new_coefficients[count] = poly.coefficients[i];
                new_exponents[count] = poly.exponents[i];
                count++;
            }
        }

        for (int i = 0; i < count; i++)
        {
            if (new_coefficients[i] != 0)
            {
                count_two++;
            }
        }

        double[] shortened_coefficients = new double[count_two];
        int[] shortened_exponents = new int[count_two];

        for (int i = 0; i < count; i++)
        {
            if (new_coefficients[i] != 0)
            {
                shortened_coefficients[count_three] = new_coefficients[i];
                shortened_exponents[count_three] = new_exponents[i];
                count_three++;
            }
        }

        return new Polynomial(shortened_coefficients, shortened_exponents);
    }

    double evaluate(double value)
    {
        double sum = 0;

        for (int i = 0; i < coefficients.length; i++)
        {
            sum += coefficients[i] * Math.pow(value, exponents[i]);
        }

        return sum;
    }

    boolean hasRoot(double root)
    {
        return (evaluate(root) == 0);
    }

    public Polynomial multiply(Polynomial poly)
    {
        int length = this.coefficients.length * poly.coefficients.length;
        double[] new_coefficients = new double[length];
        int[] new_exponents = new int[length];
        int count = 0;
        int count_two = 0;

        for (int i = 0; i < this.coefficients.length; i++)
        {
            for (int j = 0; j < poly.coefficients.length; j++)
            {
                new_coefficients[count] = this.coefficients[i] * poly.coefficients[j];
                new_exponents[count] = this.exponents[i] + poly.exponents[j];
                count++;
            }
        }

        for (int i = 0; i < length; i++)
        {
            for (int j = i + 1; j < length; j++)
            {
                if (new_exponents[i] == new_exponents[j])
                {
                    new_coefficients[i] += new_coefficients[j];
                    new_coefficients[j] = 0;
                    new_exponents[j] = 0;
                }
            }
        }

        count = 0;

        for (int i = 0; i < length; i++)
        {
            if (new_coefficients[i] != 0)
            {
                count++;
            }
        }

        double[] shortened_coefficients = new double[count];
        int[] shortened_exponents = new int[count];

        for (int i = 0; i < length; i++)
        {
            if (new_coefficients[i] != 0)
            {
                shortened_coefficients[count_two] = new_coefficients[i];
                shortened_exponents[count_two] = new_exponents[i];
                count_two++;
            }
        }

        return new Polynomial(shortened_coefficients, shortened_exponents);
    }

    public void saveToFile(String file) throws IOException
    {
        FileWriter output = new FileWriter(file, false);
        String poly = "";

        for (int i = 0; i < coefficients.length; i++)
        {
            if (coefficients[i] == 0)
            {
                continue;
            }
            
            if (coefficients[i] % 1 == 0)
            {
                poly += Integer.toString((int)coefficients[i]);
            }

            else
            {
                poly += Double.toString(coefficients[i]);
            }

            if (exponents[i] != 0)
            {
                poly += "x";
                poly += Integer.toString(exponents[i]);
            }

            if (i != coefficients.length - 1 && coefficients[i + 1] != 0)
            {
                if (coefficients[i + 1] > 0)
                {
                    poly += "+";
                }
            }
        }

        output.write(poly);
        output.close();
    }
}