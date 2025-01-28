
using Main.Decorator;
using Main.Domain;
using Main.Factory;

static MessageTask[] CreateMessages()
{
    var msg1 = new MessageTask(1,"1","msg1","a","b", DateTime.Now);
    var msg2 = new MessageTask(2,"2","msg2","a","b", DateTime.Now);
    var msg3 = new MessageTask(3,"3","msg3","a","b", DateTime.Now);
    var msg4 = new MessageTask(4,"4","msg4","a","b", DateTime.Now);
    var msg5 = new MessageTask(5,"5","msg5","a","b", DateTime.Now);

    return [msg1, msg2, msg3, msg4, msg5];
}

var messageTasks = CreateMessages();
Strategy strategy;
if (args[0] == "lifo")
{
    strategy = Strategy.Lifo;
}
else
{
    strategy = Strategy.Fifo;
}
var strategyTaskRunner = new StrategyTaskRunner(strategy);
foreach (var message in messageTasks)
    strategyTaskRunner.AddTask(message);
var delayTaskRunner = new DelayTaskRunner(strategyTaskRunner);
var printerTaskRunner = new PrinterTaskRunner(delayTaskRunner);
printerTaskRunner.ExecuteAll();
