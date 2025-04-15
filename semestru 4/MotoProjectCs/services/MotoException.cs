namespace moto.services;

public class MotoException : Exception
{
    public MotoException():base() { }

    public MotoException(String msg) : base(msg) { }

    public MotoException(String msg, Exception ex) : base(msg, ex) { }

}