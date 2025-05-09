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
            string connectionString = "Data Source=10.211.55.2;Initial Catalog=Bank;Persist Security Info=True;User ID=SA;Password=Strong.Pwd-123;Encrypt=True;Trust Server Certificate=True";
            conn = new SqlConnection(connectionString);

            parentAdapter = new SqlDataAdapter("SELECT * FROM Loans", conn);
            childAdapter = new SqlDataAdapter("SELECT * FROM Payments", conn);

            SqlCommandBuilder childBuilder = new SqlCommandBuilder(childAdapter);

            ds = new DataSet();
            parentAdapter.Fill(ds, "Loans");
            childAdapter.Fill(ds, "Payments");

            DataRelation relation = new DataRelation(
                "FK_Loans_Payments",
                ds.Tables["Loans"].Columns["loan_id"],
                ds.Tables["Payments"].Columns["loan_id"]);
            ds.Relations.Add(relation);

            parentBinding = new BindingSource(ds, "Loans");
            dataGridViewParent.DataSource = parentBinding;

            childBinding = new BindingSource(parentBinding, "FK_Loans_Payments");
            dataGridViewChild.DataSource = childBinding;
        }

        private void dataGridViewParent_SelectionChanged(object sender, EventArgs e)
        {

        }

        private void buttonAddChild_Click(object sender, EventArgs e)
        {
            if (parentBinding.Current == null) return;

            DataRowView parentRow = (DataRowView)parentBinding.Current;
            int loanId = (int)parentRow["loan_id"];

            decimal amount;
            DateTime paymentDate;
            string type = textBoxType.Text;

            try
            {
                amount = decimal.Parse(textBoxAmount.Text);
            }
            catch (FormatException)
            {
                MessageBox.Show("Invalid amount format. Please enter a valid decimal number.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            try
            {
                paymentDate = DateTime.Parse(textBoxPaymentDate.Text);
            }
            catch (FormatException)
            {
                MessageBox.Show("Invalid payment date format. Please enter a valid date.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                return;
            }

            DataRow newChild = ds.Tables["Payments"].NewRow();
            newChild["loan_id"] = loanId;
            newChild["amount"] = amount;
            newChild["payment_date"] = paymentDate;
            newChild["type"] = type;

            ds.Tables["Payments"].Rows.Add(newChild);

            using (SqlCommand cmd = new SqlCommand("INSERT INTO Payments (loan_id, amount, payment_date, type) OUTPUT INSERTED.payment_id VALUES (@loan_id, @amount, @payment_date, @type)", conn))
            {
                cmd.Parameters.AddWithValue("@loan_id", loanId);
                cmd.Parameters.AddWithValue("@amount", newChild["amount"]);
                cmd.Parameters.AddWithValue("@payment_date", newChild["payment_date"]);
                cmd.Parameters.AddWithValue("@type", newChild["type"]);

                if (conn.State != ConnectionState.Open)
                {
                    conn.Open();
                }

                int newPaymentId = (int)cmd.ExecuteScalar();

                newChild["payment_id"] = newPaymentId;
            }

            childAdapter.Update(ds, "Payments");
            ds.Tables["Payments"].AcceptChanges();
        }


        private void buttonDelete_Click(object sender, EventArgs e)
        {
            if (childBinding.Current == null) return;

            DataRowView childRow = (DataRowView)childBinding.Current;
            childRow.Delete();
            childAdapter.Update(ds, "Payments");
            ds.Tables["Payments"].AcceptChanges();
        }
    }
}
