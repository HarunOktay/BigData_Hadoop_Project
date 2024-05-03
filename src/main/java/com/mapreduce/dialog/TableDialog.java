package com.mapreduce.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TableDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Create the dialog.
	 */
	public TableDialog(String[] headers, Object[][] rows) {
		setBounds(100, 100, 1099, 684);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			table = new JTable();
			table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 18));
			table.setFont(new Font("Tahoma", Font.PLAIN, 14));
			table.setModel(new DefaultTableModel(rows, headers));
			int width = table.getSize().width;
			Enumeration<TableColumn> iter = table.getColumnModel().getColumns();
			while (iter.hasMoreElements()) {
				iter.nextElement().setPreferredWidth(width);
			}
		}
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			scrollPane = new JScrollPane(table);
			contentPanel.add(scrollPane);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String str = "";
						for (int i = 0; i < rows.length; i++) {
							int j;
							for (j = 0; j < rows[i].length - 1; j++) {
								str += rows[i][j] + "\t";
							}
							str += rows[i][j] + "\n";
						}
						try {

                            JFileChooser fileChooser = new JFileChooser();
                            int returnValue = fileChooser.showSaveDialog(null);
                            if(returnValue == JFileChooser.APPROVE_OPTION){
                                File selectedFile = fileChooser.getSelectedFile();
                                String filePath = selectedFile.getAbsolutePath();
                                saveStringToFile(str,selectedFile);
                                System.out.println("Wrote to " + filePath);
                            }

						} catch (Exception e1) {
							e1.printStackTrace();
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

    void saveStringToFile(String data,File file) throws FileNotFoundException{
        System.out.println("\n");
        
        PrintWriter writer = new PrintWriter(file);
        writer.write(data);
        writer.flush();
        writer.close();

    }

}
