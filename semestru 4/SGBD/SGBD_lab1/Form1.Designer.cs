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
            this.buttonAddChild.Location = new Point(12, 850);
            this.buttonAddChild.Name = "buttonAddChild";
            this.buttonAddChild.Size = new Size(150, 46);
            this.buttonAddChild.TabIndex = 2;
            this.buttonAddChild.Text = "Add Child";
            this.buttonAddChild.UseVisualStyleBackColor = true;
            this.buttonAddChild.Click += new EventHandler(this.buttonAddChild_Click);
            // 
            // buttonDelete
            // 
            this.buttonDelete.Location = new Point(180, 850);
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.Size = new Size(150, 46);
            this.buttonDelete.TabIndex = 3;
            this.buttonDelete.Text = "Delete Child";
            this.buttonDelete.UseVisualStyleBackColor = true;
            this.buttonDelete.Click += new EventHandler(this.buttonDelete_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new SizeF(13F, 32F);
            this.AutoScaleMode = AutoScaleMode.Font;
            this.ClientSize = new Size(1200, 1200);
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
        }
    }
}
