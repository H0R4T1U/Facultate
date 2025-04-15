using Gtk;
using log4net;
using System;
using moto.model;

namespace ChatClientGTK
{
    public class MotoWindow : Window
    {
        private readonly GTKClientCtrl ctrl;

        private readonly ListStore raceStore;
        private readonly ListStore playerStore;

        private Entry teamSearch, playerNameAdd, playerCodeAdd, playerTeamAdd;
        private Button searchTeamButton, addPlayerButton, logoutButton;
        private TreeView raceTable, playerTable;

        private static readonly ILog log = LogManager.GetLogger(typeof(MotoWindow));

        public MotoWindow(GTKClientCtrl ctrl, string title) : base(title)
        {
            this.ctrl = ctrl;

            raceStore = new ListStore(typeof(string), typeof(string), typeof(string));
            playerStore = new ListStore(typeof(string), typeof(string));

            SetDefaultSize(1000, 600);
            SetPosition(WindowPosition.Center);

            VBox mainLayout = new VBox(false, 5);

            // Race Table and Team Search
            HBox raceSection = new HBox(false, 5);
            raceTable = CreateTableView(raceStore, new[] { "ID", "Number of Players", "Engine Type" });
            raceSection.PackStart(raceTable, true, true, 5);
            PopulateRaceList();
            VBox teamSearchBox = new VBox(false, 5);
            teamSearchBox.PackStart(new Label("Echipa"), false, false, 5);
            teamSearch = new Entry { PlaceholderText = "Search" };
            teamSearchBox.PackStart(teamSearch, false, false, 5);
            searchTeamButton = new Button("Cauta");
            searchTeamButton.Clicked += HandleSearchTeam;
            teamSearchBox.PackStart(searchTeamButton, false, false, 5);
            raceSection.PackStart(teamSearchBox, false, false, 5);

            mainLayout.PackStart(raceSection, true, true, 5);

            // Player Table and Add Player
            HBox playerSection = new HBox(false, 5);
            playerTable = CreateTableView(playerStore, new[] { "Name", "Team" });
            playerSection.PackStart(playerTable, true, true, 5);

            VBox addPlayerBox = new VBox(false, 5);
            addPlayerBox.PackStart(new Label("Nume"), false, false, 5);
            playerNameAdd = new Entry { PlaceholderText = "Nume" };
            addPlayerBox.PackStart(playerNameAdd, false, false, 5);

            addPlayerBox.PackStart(new Label("CNP"), false, false, 5);
            playerCodeAdd = new Entry { PlaceholderText = "Cnp" };
            addPlayerBox.PackStart(playerCodeAdd, false, false, 5);

            addPlayerBox.PackStart(new Label("Echipa"), false, false, 5);
            playerTeamAdd = new Entry { PlaceholderText = "Echipa" };
            addPlayerBox.PackStart(playerTeamAdd, false, false, 5);

            addPlayerButton = new Button("Adauga Jucator");
            addPlayerButton.Clicked += HandleAddPlayer;
            addPlayerBox.PackStart(addPlayerButton, false, false, 5);

            playerSection.PackStart(addPlayerBox, false, false, 5);

            mainLayout.PackStart(playerSection, true, true, 5);

            // Logout Button
            logoutButton = new Button("LOGOUT");
            logoutButton.Clicked += HandleLogout;
            ctrl.updateEvent += userUpdate;

            mainLayout.PackStart(logoutButton, false, false, 5);
            
            Add(mainLayout);

        }

        private void PopulateRaceList()
        {
            try
            {
                log.Debug("PopulateRaceList");
                raceStore.Clear();
                var races = ctrl.GetAllRaces();
                foreach (var race in races)
                {
                    raceStore.AppendValues(race.Id, race.NoPlayers, race.EngineType);
                }
            }
            catch (Exception ex)
            {
                log.Error("Error populating race list: " + ex.Message);
                ShowError("Failed to load races from the server.");
            }
        }

        private TreeView CreateTableView(ListStore store, string[] columnTitles)
        {
            TreeView treeView = new TreeView(store);
            foreach (var title in columnTitles)
            {
                TreeViewColumn column = new TreeViewColumn { Title = title };
                CellRendererText renderer = new CellRendererText();
                column.PackStart(renderer, true);
                column.AddAttribute(renderer, "text", Array.IndexOf(columnTitles, title));
                treeView.AppendColumn(column);
            }

            return treeView;
        }

        private void HandleSearchTeam(object sender, EventArgs e)
        {
            string teamName = teamSearch.Text;
            teamSearch.Text = "";
            TreeIter selected;
            if (!raceTable.Selection.GetSelected(out selected))
            {
                ShowError("Please select a race before searching.");
                return;
            }


            try
            {
                string raceIdStr = (string)raceStore.GetValue(selected, 0); // Retrieve as string

                int raceId = int.TryParse(raceIdStr, out int raceIdInt) ? raceIdInt : 0;
                Player[] players = ctrl.SearchTeam(teamName, raceId);
                playerStore.Clear();
                foreach (var player in players)
                {
                    playerStore.AppendValues(player.Name, player.Team);
                }
            }
            catch (Exception ex)
            {
                log.Error("Error populating Player list: " + ex.Message);
                ShowError("Failed to load races from the server.");
            }
        }

        private void HandleAddPlayer(object sender, EventArgs e)
        {
            TreeIter selected;
            if (!raceTable.Selection.GetSelected(out selected))
            {
                ShowError("Please select a race before adding a player.");
                return;
            }

            string raceIdStr = (string)raceStore.GetValue(selected, 0); // Retrieve as string

            int raceId = int.TryParse(raceIdStr, out int raceIdInt) ? raceIdInt : 0;
            string playerName = playerNameAdd.Text;
            string playerCode = playerCodeAdd.Text;
            string teamName = playerTeamAdd.Text;
            
            playerNameAdd.Text = "";
            playerCodeAdd.Text = "";
            playerTeamAdd.Text = "";
            
            ctrl.AddPlayer(playerName, playerCode, teamName, raceId);
        }

        private void HandleLogout(object sender, EventArgs e)
        {
            
            ctrl.Logout();
            ctrl.updateEvent -= userUpdate;
            Application.Quit();
        }

        private void ShowError(string message)
        {
            MessageDialog md = new MessageDialog(this, DialogFlags.DestroyWithParent, MessageType.Error, ButtonsType.Ok,
                message);
            md.Run();
            md.Dispose();
        }

        public void userUpdate(object? sender, MotoUserEventArgs e)
        {
            try
            {
                    if (e.UserEventType == MotoUserEvent.Player_ADDED)
                {
                    var player = e.Data.ToString().Split(',');
                    string name = player[0].Split(':')[1].Trim();
                    string code = player[1].Split(':')[1].Trim();
                    int team = int.Parse(player[2].Split(':')[1].Trim());
                    Application.Invoke(delegate
                    {
                        try
                        {
                            PopulateRaceList();
                            int rowCount = playerStore.IterNChildren(); 
                            if(rowCount > 0)
                            {
                                playerStore.AppendValues(name, team);
                            }

                            
                        }
                        catch (Exception ex)
                        {
                            Console.WriteLine("Error in Invoke: " + ex);
                        }

                    log.DebugFormat("[ChatWindow] player added {0}", player);
                    });
                }
            }
            catch (Exception ex)
            {
                log.Error("Error durring update  event"+ ex.Message);
            }
        }
    }
}