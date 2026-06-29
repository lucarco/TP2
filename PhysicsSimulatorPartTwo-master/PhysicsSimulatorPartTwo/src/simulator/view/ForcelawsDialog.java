package simulator.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;


import simulator.control.Controller;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

class ForcelawsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int _status;
	private JsonTableModel _dataTableModel;
	private Controller _ctrl ;
	private JComboBox<String> selector;
	private JSONObject selected;
	private String type;
	private int chosen;


	// This table model stores internally the content of the table. Use
	// getData() to get the content as JSON.
	//
	private class JsonTableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] _header = { "Key", "Value", "Description" };
		String[][] _data;
		
		JsonTableModel() {
			update(0);
		}
		public void update(int index) {
			JSONObject info = _ctrl.getForceLawsInfo().get(index);
			JSONObject data = info.getJSONObject("data");
			
			
			Set<String> keys = data.keySet();
			_data = new String[keys.size()][3];
			
			for(int i=0; i<keys.size(); i++) {
				for(int j=0; j<3; j++) {
					_data[i][j] = "";
					
				}
			}
			
			int i = 0;
			for(String key : keys) {
				_data[i][0] = key;
				_data[i][2] = data.getString(key);
				i++;
			}
			
			
			
			fireTableStructureChanged();
			
		}


		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		@Override
		public int getRowCount() {
			return _data.length;
		}

		@Override
		public int getColumnCount() {
			return _header.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return _data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			_data[rowIndex][columnIndex] = o.toString();
		}

		// Method getData() returns a String corresponding to a JSON structure
		// with column 1 as keys and column 2 as values.

		// This method return the coIt is important to build it as a string, if
		// we create a corresponding JSONObject and use put(key,value), all values
		// will be added as string. This also means that if users want to add a
		// string value they should add the quotes as well as part of the
		// value (2nd column).
		//
		public String getData() {
			StringBuilder s = new StringBuilder();
			s.append('{');
			for (int i = 0; i < _data.length; i++) {
				if (!_data[i][0].isEmpty() && !_data[i][1].isEmpty()) {
					s.append('"');
					s.append(_data[i][0]);
					s.append('"');
					s.append(':');
					s.append(_data[i][1]);
					s.append(',');
				}
			}

			if (s.length() > 1)
				s.deleteCharAt(s.length() - 1);
			s.append('}');

			return s.toString();
		}
	}

	ForcelawsDialog(Frame parent, Controller ctrl) {

		super(parent, true);
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {

		_status = 0;

		setTitle("Build JSON from Table");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		// help
		JLabel help = new JLabel("<html><p>Select a force law and provide values for"
				+ " the parameters in the Value column (default values are used for"
				+ " parameters with no value</p></html>");

		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// data table
		_dataTableModel = new JsonTableModel();
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resized rows to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		String[] names = new  String[_ctrl.getForceLawsInfo().size()];
		for(int i = 0; i < names.length; i++) {
			names[i]=_ctrl.getForceLawsInfo().get(i).getString("desc");
		}
		
		selector = new JComboBox<String>(names);
		selector.setSelectedIndex(0);
		selector.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 chosen = selector.getSelectedIndex();
				_dataTableModel.update(chosen);
			}
		});

		selector.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(selector);
		// buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(buttonsPanel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ForcelawsDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

	

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				ForcelawsDialog.this.setVisible(false);
				selected = _ctrl.getForceLawsInfo().get(chosen);
				 JSONObject o = new JSONObject();
				 o.put("type", selected.getString("type"));
				 o.put("data", new JSONObject(_dataTableModel.getData()));

				 try {
				_ctrl.setForcesLaws(o);
				 }catch(Exception ex) {
					 JOptionPane.showMessageDialog(null, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
				 }
						
						
				
			}
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(400, 400));
		pack();
		setResizable(false); // change to 'true' if you want to allow resizing
		setVisible(false); // we will show it only whe open is called
	}

	public int open() {

		if (getParent() != null)
			setLocation(
					getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, 
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return _status;
	}

	public JSONObject getJSON() {
		JSONObject newLaw = new JSONObject();

		newLaw.put("type", selected.getString("type"));
		newLaw.put("data", new JSONObject(_dataTableModel.getData()));
		return newLaw;
	}

}