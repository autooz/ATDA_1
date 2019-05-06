package mobi.tet_a_tet.atda.mutual.mut_ulils.poligons;

/**
 * Created by oleg on 11.09.15.
 */
public class Point
{
    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float x;
    public float y;

    @Override
    public String toString()
    {
        return String.format("(%.7f,%.7f)", x, y);
    }
}