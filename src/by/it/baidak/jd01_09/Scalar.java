package by.it.baidak.jd01_09;

/**
 * Created by user on 27.11.2018.
 */
class Scalar extends Var {

    double value;

    Scalar(double value) {
        this.value = value;
    }

    Scalar (String value){
        this.value=Double.parseDouble(value);
    }

    Scalar (Scalar other){
        this.value = other.value;
    }

    @Override
    public Var add(Var other) {
        if (other instanceof Scalar) {
            Scalar operand2 = (Scalar) other;
            double res = this.value + operand2.value;
            return new Scalar(res);
        } else
            return other.add(this);
    }

    @Override
    public Var sub(Var other) {
        if (other instanceof Scalar) {
            Scalar operand2 = (Scalar) other;
            double res = this.value - operand2.value;
            return new Scalar(res);
        } else
            return other.sub(this).mul(new Scalar(-1.0));
    }

    @Override
    public Var mul(Var other) {
        if (other instanceof Scalar) {
            Scalar operand2 = (Scalar) other;
            double res = this.value * operand2.value;
            return new Scalar(res);
        } else
            return other.mul(this);
    }

    @Override
    public Var div(Var other) {
        if (other instanceof Scalar) {
            Scalar operand2 = (Scalar) other;
            double res = this.value / operand2.value;
            return new Scalar(res);
        } else
            return super.div(other);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
