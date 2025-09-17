public class Polynomial
{
    double [] coefficients = new double[1024];

    public Polynomial()
    {
        coefficients = new double[] {0};
    }

    public Polynomial(double [] coefficients_to_add)
    {
        coefficients = coefficients_to_add;
    }

    public Polynomial add(Polynomial poly)
    {
        double [] new_coefficients;
        
        if (poly.coefficients.length < this.coefficients.length)
        {
            new_coefficients = new double[this.coefficients.length];
            
            for (int i = 0; i < this.coefficients.length; i++)
            {
                if (poly.coefficients.length > i)
                {
                    new_coefficients[i] = this.coefficients[i] + poly.coefficients[i];
                }

                else
                {
                    new_coefficients[i] = this.coefficients[i];
                }
            }
        }

        else
        {
            new_coefficients = new double[poly.coefficients.length];

            for (int i = 0; i < poly.coefficients.length; i++)
            {
                if (this.coefficients.length > i)
                {
                    new_coefficients[i] = this.coefficients[i] + poly.coefficients[i];
                }

                else
                {
                    new_coefficients[i] = poly.coefficients[i];
                }
            }
        }

        return new Polynomial(new_coefficients);
    }

    double evaluate(double value)
    {
        double sum = 0;

        for (int i = 0; i < coefficients.length; i++)
        {
            sum += coefficients[i] * Math.pow(value, i);
        }

        return sum;
    }

    boolean hasRoot(double root)
    {
        return (evaluate(root) == 0);
    }
}