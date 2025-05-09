// File: Form1.Designer.cs
namespace SGBD_lab1
{
    partial class Form1
    {
        private System.ComponentModel.IContainer components = null;
        private DataGridView dataGridViewParent;
        private DataGridView dataGridViewChild;
        private Button buttonAddChild;
        private Button buttonDelete;
        private TextBox textBoxAmount;
        private TextBox textBoxPaymentDate;
        private TextBox textBoxType;
        private Label labelAmount;
        private Label labelPaymentDate;
        private Label labelType;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
                components.Dispose();
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.dataGridViewParent = new DataGridView();
            this.dataGridViewChild = new DataGridView();
            this.buttonAddChild = new Button();
            this.buttonDelete = new Button();
            this.textBoxAmount = new TextBox();
            this.textBoxPaymentDate = new TextBox();
            this.textBoxType = new TextBox();
            this.labelAmount = new Label();
            this.labelPaymentDate = new Label();
            this.labelType = new Label();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridViewParent
            // 
            this.dataGridViewParent.AllowUserToAddRows = false;
            this.dataGridViewParent.AllowUserToDeleteRows = false;
            this.dataGridViewParent.Location = new Point(12, 12);
            this.dataGridViewParent.MultiSelect = false;
            this.dataGridViewParent.Name = "dataGridViewParent";
            this.dataGridViewParent.ReadOnly = true;
            this.dataGridViewParent.RowHeadersWidth = 82;
            this.dataGridViewParent.RowTemplate.Height = 41;
            this.dataGridViewParent.Size = new Size(1176, 400);
            this.dataGridViewParent.TabIndex = 0;
            this.dataGridViewParent.SelectionChanged += new EventHandler(this.dataGridViewParent_SelectionChanged);
            // 
            // dataGridViewChild
            // 
            this.dataGridViewChild.AllowUserToAddRows = false;
            this.dataGridViewChild.AllowUserToDeleteRows = false;
            this.dataGridViewChild.Location = new Point(12, 430);
            this.dataGridViewChild.MultiSelect = false;
            this.dataGridViewChild.Name = "dataGridViewChild";
            this.dataGridViewChild.ReadOnly = true;
            this.dataGridViewChild.RowHeadersWidth = 82;
            this.dataGridViewChild.RowTemplate.Height = 41;
            this.dataGridViewChild.Size = new Size(1176, 400);
            this.dataGridViewChild.TabIndex = 1;
            // 
            // buttonAddChild
            // 
            this.buttonAddChild.Location = new Point(12, 1050);
            this.buttonAddChild.Name = "buttonAddChild";
            this.buttonAddChild.Size = new Size(150, 46);
            this.buttonAddChild.TabIndex = 2;
            this.buttonAddChild.Text = "Add Child";
            this.buttonAddChild.UseVisualStyleBackColor = true;
            this.buttonAddChild.Click += new EventHandler(this.buttonAddChild_Click);
            // 
            // buttonDelete
            // 
            this.buttonDelete.Location = new Point(180, 1050);
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.Size = new Size(150, 46);
            this.buttonDelete.TabIndex = 3;
            this.buttonDelete.Text = "Delete Child";
            this.buttonDelete.UseVisualStyleBackColor = true;
            this.buttonDelete.Click += new EventHandler(this.buttonDelete_Click);
            // 
            // textBoxAmount
            // 
            this.textBoxAmount.Location = new Point(12, 900);
            this.textBoxAmount.Name = "textBoxAmount";
            this.textBoxAmount.Size = new Size(150, 39);
            this.textBoxAmount.TabIndex = 4;
            // 
            // textBoxPaymentDate
            // 
            this.textBoxPaymentDate.Location = new Point(180, 900);
            this.textBoxPaymentDate.Name = "textBoxPaymentDate";
            this.textBoxPaymentDate.Size = new Size(150, 39);
            this.textBoxPaymentDate.TabIndex = 5;
            // 
            // textBoxType
            // 
            this.textBoxType.Location = new Point(348, 900);
            this.textBoxType.Name = "textBoxType";
            this.textBoxType.Size = new Size(150, 39);
            this.textBoxType.TabIndex = 6;
            // 
            // labelAmount
            // 
            this.labelAmount.AutoSize = true;
            this.labelAmount.Location = new Point(12, 860);
            this.labelAmount.Name = "labelAmount";
            this.labelAmount.Size = new Size(91, 32);
            this.labelAmount.TabIndex = 7;
            this.labelAmount.Text = "Amount";
            // 
            // labelPaymentDate
            // 
            this.labelPaymentDate.AutoSize = true;
            this.labelPaymentDate.Location = new Point(180, 860);
            this.labelPaymentDate.Name = "labelPaymentDate";
            this.labelPaymentDate.Size = new Size(156, 32);
            this.labelPaymentDate.TabIndex = 8;
            this.labelPaymentDate.Text = "Payment Date";
            // 
            // labelType
            // 
            this.labelType.AutoSize = true;
            this.labelType.Location = new Point(348, 860);
            this.labelType.Name = "labelType";
            this.labelType.Size = new Size(63, 32);
            this.labelType.TabIndex = 9;
            this.labelType.Text = "Type";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new SizeF(13F, 32F);
            this.AutoScaleMode = AutoScaleMode.Font;
            this.ClientSize = new Size(1200, 1200);
            this.Controls.Add(this.labelType);
            this.Controls.Add(this.labelPaymentDate);
            this.Controls.Add(this.labelAmount);
            this.Controls.Add(this.textBoxType);
            this.Controls.Add(this.textBoxPaymentDate);
            this.Controls.Add(this.textBoxAmount);
            this.Controls.Add(this.buttonDelete);
            this.Controls.Add(this.buttonAddChild);
            this.Controls.Add(this.dataGridViewChild);
            this.Controls.Add(this.dataGridViewParent);
            this.Name = "Form1";
            this.Text = "Parent-Child Demo";
            this.Load += new EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewParent)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridViewChild)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }
    }
}
