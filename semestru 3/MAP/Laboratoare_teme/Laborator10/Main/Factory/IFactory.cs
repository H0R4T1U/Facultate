
using Main.Domain;

namespace Main.Factory;

public interface IFactory
{
    IContainer CreateContainer(Strategy strategy);
}