package conserveET.client;

import conserveET.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Prototype implements EntryPoint {
	
	private HorizontalPanel buttonPanel = new HorizontalPanel();

	private Button energyButton = new Button("Energy");	
	private Button avatarButton = new Button("Avatar");
	private Button rankButton = new Button("Rank");
	private TextBox rank = new TextBox();

	/**
	 * Create a remote service proxy to talk to the server-side service.
	 */
	private final ProtoAsync protoService = GWT
			.create(Proto.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		energyButton.setText("Energy");
		RootPanel.get();
		RootPanel.get("ConserveET");
		rank.setText("24th");
		buttonPanel.add(energyButton);
		buttonPanel.add(avatarButton);
		buttonPanel.add(rankButton);
		
	    Runnable onLoadCallback = new Runnable() {
	        public void run() {
	   
	          // Create a pie chart visualization.
	          PieChart pie = new PieChart(createTable(), createOptions());

	          pie.addSelectHandler(createSelectHandler(pie));
	          RootPanel.get("ConserveET").add(pie);
	        }
	      };

	      VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);



		// Use RootPanel.get() to get the entire body element
		RootPanel.get("ConserveET").add(buttonPanel, 0, 126);
		RootPanel.get("ConserveET").add(rank, 240, 56);
		rank.setSize("66px", "16px");
		buttonPanel.setSize("362px", "28px");
		
		// We can set the id of a widget by accessing its Element
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);

	}
	
	  private Options createOptions() {
		    Options options = Options.create();
		    options.setWidth(200);
		    options.setHeight(120);
		    options.set3D(true);
		    options.setTitle("Your Energy Usage");
		    return options;
		  }

		  private SelectHandler createSelectHandler(final PieChart chart) {
		    return new SelectHandler() {
		      @Override
		      public void onSelect(SelectEvent event) {
		        String message = "";
		        
		        // May be multiple selections.
		        JsArray<Selection> selections = chart.getSelections();

		        for (int i = 0; i < selections.length(); i++) {
		          // add a new line for each selection
		          message += i == 0 ? "" : "\n";
		          
		          Selection selection = selections.get(i);

		          if (selection.isCell()) {
		            // isCell() returns true if a cell has been selected.
		            
		            // getRow() returns the row number of the selected cell.
		            int row = selection.getRow();
		            // getColumn() returns the column number of the selected cell.
		            int column = selection.getColumn();
		            message += "Your energy usage";
		          } else if (selection.isRow()) {
		            // isRow() returns true if an entire row has been selected.
		            
		            // getRow() returns the row number of the selected row.
		            int row = selection.getRow();
		            message += "row " + row + " selected";
		          } else {
		            // unreachable
		            message += "Pie chart selections should be either row selections or cell selections.";
		          }
		        }
		        
		        Window.alert(message);
		      }
		    };
		  }

		  private AbstractDataTable createTable() {
		    DataTable data = DataTable.create();
		    data.addColumn(ColumnType.STRING, "Appliance");
		    data.addColumn(ColumnType.NUMBER, "kWh");
		    data.addRows(2);
		    data.setValue(0, 0, "Fridge");
		    data.setValue(0, 1, 14);
		    data.setValue(1, 0, "TV");
		    data.setValue(1, 1, 10);
		    return data;
		  }
}
