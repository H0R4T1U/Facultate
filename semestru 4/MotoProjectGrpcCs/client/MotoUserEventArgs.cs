using moto.model;

namespace MotoClientGTK;

public enum MotoUserEvent
{
    Player_ADDED
} ;
public class MotoUserEventArgs : EventArgs
{
    private readonly MotoUserEvent userEvent;
    private readonly Object data;

    public MotoUserEventArgs(MotoUserEvent userEvent, Object data)
    {
        this.userEvent = userEvent;
        this.data = data;
    }

    public MotoUserEvent UserEventType
    {
        get { return userEvent; }
    }

    public Object Data
    {
        get { return data; }
    }
}