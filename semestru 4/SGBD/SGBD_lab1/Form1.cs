// File: Form1.cs
using Microsoft.Data.SqlClient;
using System.Data;

namespace SGBD_lab1
{
    public partial class Form1 : Form
    {
        private DataSet ds;
        private SqlDataAdapter parentAdapter;
        private SqlDataAdapter childAdapter;
        private SqlConnection conn;
        private BindingSource parentBinding;
        private BindingSource childBinding;

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            // 1. Prepare your connection string
            conn = new SqlConnection(connectionString);

            // 2. Initialize adapters
            parentAdapter = new SqlDataAdapter("SELECT * FROM Loans", conn);
            childAdapter = new SqlDataAdapter("SELECT * FROM Payments", conn);

            // 3. Create commands for child table insert/update/delete
            SqlCommandBuilder childBuilder = new SqlCommandBuilder(childAdapter);

            // 4. Fill DataSet with two tables
            ds = new DataSet();
            parentAdapter.Fill(ds, "Loans");
            childAdapter.Fill(ds, "Payments");

            // 5. Set up a DataRelation so that selecting a parent row automatically filters child rows
            DataRelation relation = new DataRelation(
                "FK_Loans_Payments",
                ds.Tables["Loans"].Columns["loan_id"],
                ds.Tables["Payments"].Columns["loan_id"]);
            ds.Relations.Add(relation);

            // 6. Bind DataGridViews
            parentBinding = new BindingSource(ds, "Loans");
            dataGridViewParent.DataSource = parentBinding;

            // Child binding will be based on the relation
            childBinding = new BindingSource(parentBinding, "FK_Loans_Payments");
            dataGridViewChild.DataSource = childBinding;
        }

        private void dataGridViewParent_SelectionChanged(object sender, EventArgs e)
        {
            // Automatically handled by the relation-based BindingSource
            // This event is triggered whenever the selected parent row changes
        }

        private void buttonAddChild_Click(object sender, EventArgs e)
        {
            if (parentBinding.Current == null) return;

            // Example of inserting a new child row
            DataRowView parentRow = (DataRowView)parentBinding.Current;
            int loanId = (int)parentRow["loan_id"];

            DataRow newChild = ds.Tables["Payments"].NewRow();
            newChild["loan_id"] = loanId;
            newChild["amount"] = 100; // Set default values as needed
            newChild["payment_date"] = DateTime.Now;
            newChild["type"] = "New Payment"; // Set default values as needed

            // Add the new row to the DataTable
            ds.Tables["Payments"].Rows.Add(newChild);

            // Create a new SqlCommand to insert the row and retrieve the new identity value
            using (SqlCommand cmd = new SqlCommand("INSERT INTO Payments (loan_id, amount, payment_date, type) OUTPUT INSERTED.payment_id VALUES (@loan_id, @amount, @payment_date, @type)", conn))
            {
                cmd.Parameters.AddWithValue("@loan_id", loanId);
                cmd.Parameters.AddWithValue("@amount", newChild["amount"]);
                cmd.Parameters.AddWithValue("@payment_date", newChild["payment_date"]);
                cmd.Parameters.AddWithValue("@type", newChild["type"]);

                // Open the connection if it's not already open
                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }

                // Execute the command and retrieve the new identity value
                int newPaymentId = (int)cmd.ExecuteScalar();

                // Set the new identity value in the DataRow
                newChild["payment_id"] = newPaymentId;
            }

            // Update the DataSet and accept changes
            childAdapter.Update(ds, "Payments");
            ds.Tables["Payments"].AcceptChanges();
        }


        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (childBinding.Current == null) return;

            // Example of deleting the selected child row
            DataRowView childRow = (DataRowView)childBinding.Current;
            childRow.Delete();
            childAdapter.Update(ds, "Payments");
            ds.Tables["Payments"].AcceptChanges();
        }

        private void buttonUpdate_Click(object sender, EventArgs e)
        {
            if (childBinding.Current == null) return;

            // Example of updating the selected child row
            DataRowView childRow = (DataRowView)childBinding.Current;
            childRow["amount"] = 100; // Update with actual values
            childRow["payment_date"] = DateTime.Now; // Update with actual values
            childRow["type"] = "Updated Payment"; // Update with actual values

            childAdapter.Update(ds, "Payments");
            ds.Tables["Payments"].AcceptChanges();
        }
    }
}
