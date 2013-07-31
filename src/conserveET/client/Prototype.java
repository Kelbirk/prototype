package conserveET.client;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.plotOptions.PiePlotOptions;

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
	private VerticalPanel rankPanel = new VerticalPanel();
	private Button energyButton = new Button("Energy");	
	private Button avatarButton = new Button("Avatar");
	private Button menuButton = new Button("Menu");
	private Button rankButton = new Button("Rank");
	private Button rank1 = new Button("Marsha Little");
	private Button rank2 = new Button("Charles Guiteau");
	private Button rank3 = new Button("Roscoe Conkling");
	private TextBox rank = new TextBox();
	
	
	Chart pieChart = new Chart();

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
		
		pieChart.setType(Series.Type.PIE);
		pieChart.setChartTitleText("Your household energy usage");
		pieChart.setBorderWidth(50);
		pieChart.setSize(400, 400);
		Series pieSeries = pieChart.createSeries();
		pieSeries.setName("Appliances");
		pieSeries.setPoints(new Point[] {
			new Point("Fridge", 25),
			new Point("Microwave", 7),
			new Point("AC", 50)
		});
		pieChart.addSeries(pieSeries);
		RootPanel.get("ConserveET").add(pieChart);
		
	    // Listen for mouse events on the Energy button.
	    energyButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        energyClick();
	      }
	    });
		buttonPanel.add(avatarButton);
		avatarButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  avatarClick();
			    }
			  });
		buttonPanel.add(rankButton);
		rankButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  rankClick();
			  	}
			  });
		menuButton.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  menuClick();
			    }
		});

		// Use RootPanel.get() to get the entire body element
		RootPanel.get("ConserveET").add(buttonPanel, 10, 436);
		RootPanel.get("ConserveET").add(rank, 540, 138);
		rank.setSize("66px", "16px");
		buttonPanel.setSize("777px", "28px");
		
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
	
	public void energyClick() {
		RootPanel.get("ConserveET").clear();
		RootPanel.get("ConserveET").add(menuButton, 500, 500);

        Chart hourlyChart = new Chart()
        .setType(Series.Type.SPLINE)
        .setChartTitleText("Energy Usage")
        .setMarginRight(10);
        Series series = hourlyChart.createSeries()
      		   .setName("kWh hourly usage")
      		   .setPoints(new Number[] { 163, 203, 276, 408, 547, 729, 628 });
      		hourlyChart.addSeries(series);
      		RootPanel.get("ConserveET").add(hourlyChart);
	}
	public void avatarClick() {
		RootPanel.get("ConserveET").clear();
	}
	public void rankClick() {
		RootPanel.get("ConserveET").clear();
		rankPanel.add(rank1);
		rankPanel.add(rank2);
		rankPanel.add(rank3);
		RootPanel.get("ConserveET").add(rankPanel, 500, 0);
		
		
		RootPanel.get("ConserveET").add(menuButton, 500, 500);
		menuButton.setText("Menu");
		

	}
	public void menuClick() {
		RootPanel.get("ConserveET").clear();
		RootPanel.get("ConserveET").add(pieChart);
		RootPanel.get("ConserveET").add(buttonPanel);
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
