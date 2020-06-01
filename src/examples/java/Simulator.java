
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.map4j.coordinates.WCoordinate;
import org.map4j.layers.MapLayer;
import org.map4j.layers.MapMarkerDot;
import org.map4j.layers.MapMarkerImage;
import org.map4j.loaders.DefaultMBTilesController;
import org.map4j.loaders.DefaultOnlineTileController;
import org.map4j.loaders.TileLoaderController;
import org.map4j.render.MapRenderer;
import org.map4j.render.MapRenderer.MapRendererTopicListener;
import org.map4j.utils.ImageUtils;
import org.nav4j.Application;
import org.nav4j.control.SamplePopupCommand;
import org.nav4j.display.DisplayWindow;
import org.nav4j.display.SplitDisplayPanel;
import org.nav4j.menu.PlaceholderOption;
import org.nav4j.menu.SubMenuOption;


/**
 * Demonstrates the usage of Nav4J. This is a "simulated" control window that can be
 * used to present buttons on a Desktop screen (vs. physical buttons on an embedded
 * device).
 *
 * @author Joel Kozikowski
 */
public class Simulator extends JFrame implements MapRendererTopicListener {

    private static final long serialVersionUID = 1L;

    private static final int INIT_ZOOM = 15;

    private final JLabel zoomLabel;
    private final JSlider zoomValue;

    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 185;
    
    private WCoordinate simPosition = new WCoordinate(26.3356, -80.067);
    private int simHeading = 90;
    
    private TileLoaderController initialController;
    
    private boolean animate = false;
    
    public Simulator() {
        super("Nav4J Simulator");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JRootPane rootPane = this.getRootPane();
        rootPane.setLayout(new BorderLayout());

        JPanel panelTop = new JPanel();
        JPanel panelBottom = new JPanel();

        
        
        // A slider to set the map zoom level...
        zoomLabel = new JLabel("Zoom: ");
        zoomValue = new JSlider(0, 20, INIT_ZOOM);
        zoomValue.setPaintLabels(true);
        zoomValue.setPaintTicks(true);
        zoomValue.setMajorTickSpacing(5);
        zoomValue.setMinorTickSpacing(1);
        zoomValue.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int zv = zoomValue.getValue();
                Application.mapPanel.setZoom(zv);
            }
        });
        panelTop.add(zoomLabel);
        panelTop.add(zoomValue);
        Application.mapPanel.setZoom(INIT_ZOOM);
        

        // A set of buttons to represent physical buttons that would exist
        // on a physical device...
        JPanel panelButtons = new JPanel();
        panelTop.add(panelButtons);
        panelButtons.setLayout(new FlowLayout());
        TimedButton btnLeft = new TimedButton("Left");
        btnLeft.addShortClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.uiController.buttonLeftShort();;
            }
        });
        btnLeft.addLongClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.uiController.buttonLeftLong();;
            }
        });
        panelButtons.add(btnLeft);

        TimedButton btnCenter = new TimedButton("Center");
        btnCenter.addShortClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.uiController.buttonCenterShort();;
            }
        });
        btnCenter.addLongClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.uiController.buttonCenterLong();;
            }
        });
        panelButtons.add(btnCenter);
        
        TimedButton btnRight = new TimedButton("Right");
        btnRight.addShortClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.uiController.buttonRightShort();;
            }
        });
        btnRight.addLongClickListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.uiController.buttonRightLong();;
            }
        });
        panelButtons.add(btnRight);
        
        rootPane.add(panelTop, BorderLayout.NORTH);
        rootPane.add(panelBottom, BorderLayout.SOUTH);

        
        // A refresh button to refresh the map rendering (in case
        // something looks weird. Used primarily for development...
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.mapPanel.refresh(true);
            }
        });
        panelBottom.add(btnRefresh);

        
        // An "Animate" button that sets the display location
        // and heading to different values...
        JButton btnAnimate = new JButton("Animate");
        btnAnimate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animate = !animate;
            }
        });
        panelBottom.add(btnAnimate);

        
        // A "Reset" button to reset the current position on the map.
        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simPosition = new WCoordinate(26.3356, -80.067);
                simHeading = 90;
                Application.mapPanel.setPosition(simPosition);
                Application.mapPanel.setHeading(simHeading);
            }
        });
        panelBottom.add(btnReset);
        
        
        // A combo box to allow for different map tile sources to be selected...
        JComboBox<TileLoaderController> tileSourceSelector = new JComboBox<>(new TileLoaderController[] {
                initialController = new DefaultOnlineTileController("OpenStreetMap", "https://a.tile.openstreetmap.org", "png", true),
                new DefaultMBTilesController("/Users/Joel/Workspaces/cdev/geo/data/NOAA Raster South Florida Sonar.mbtiles")
        });
        tileSourceSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Application.mapPanel.setTileLoaderController((TileLoaderController) e.getItem());
            }
        });
        Application.mapPanel.setTileLoaderController(initialController);
        panelTop.add(tileSourceSelector);
        
        
        
        // A checkbox to determine if map markers should be displayed...
        final JCheckBox showMapMarker = new JCheckBox("Map markers visible");
        showMapMarker.setSelected(Application.mapPanel.isMapLayersVisible());
        showMapMarker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.mapPanel.setMapLayersVisible(showMapMarker.isSelected());
            }
        });
        panelBottom.add(showMapMarker);
        
        
        
        // A check box to determine if the split panel is visible or not...
        final JCheckBox showSplitPanel = new JCheckBox("Split panel visible");
        showSplitPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSplitPanel().setSplitVisible(showSplitPanel.isSelected());
            }
        });
        panelBottom.add(showSplitPanel);
        
       
        // Run a simulator that shows motion...
        Application.mapPanel.setPosition(simPosition);
        Timer timer = new Timer(1000, new ActionListener() {
            private double delta = 0.001;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (animate) {
                    if (simPosition.getLon() > -80.058) {
                        if (simHeading < 180) {
                            simHeading += 5;
                        }
                        else {
                          simHeading = 180;
                          simPosition.setLat(simPosition.getLat() - delta);
                        }
                    }
                    else {
                       simPosition.setLon(simPosition.getLon() + delta);
                    }
                    System.out.println("Sim heading " + simHeading + " at position is " + simPosition.toString());                
                    Application.mapPanel.setPosition(simPosition);
                    Application.mapPanel.setHeading(simHeading);
                }
            }
        });
        timer.start();

        
        // Subscribe to map changes so we can update our UI controls to match
        // the state of the map...
        MapRenderer.broker.subscribe(MapRenderer.TOPIC_CHANGED, this);
        
        pack();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - this.getWidth()) / 2, screenSize.height / 2 + 315);
    }

    
    
    public SplitDisplayPanel getSplitPanel() {
        return Application.displayWindow.getSplitPanel();
    }
    
    
    @Override
    public void onPublish(String topic, MapRenderer render) {
        // A change has happened on the map. Be sure our 
        // zoom slider has the current zoom level.
        zoomValue.setValue(render.getZoomLevel());
    }


    /**
     * Create the "main menu" structure. This is just some fake
     * values for the time being...
     */
    public static void initMainMenu() {
        Application.mainMenu.addOption(new PlaceholderOption("New Waypoint"));

        SubMenuOption subMenu = new SubMenuOption("Send Message");
        subMenu.addOption(new PlaceholderOption("I'm OK"))
               .addOption(new PlaceholderOption("Send help"))
               .addOption(new PlaceholderOption("Big Fish!"))
               .addOption(new PlaceholderOption("On my way up"))
               .addOption(new PlaceholderOption("Yet another msg"));
        
        Application.mainMenu.addOption(subMenu);
        Application.mainMenu.addOption(new SamplePopupCommand("Test Dialog"));
    }

    
    /**
     * Simulate loading user defined markers from a file...
     */
    public static void loadMapMarkers() {
        
        MapLayer markerSet = new MapLayer("Florida");
        MapLayer boca = markerSet.add("Boca Raton");
        boca.add(new MapMarkerDot("Boca Inlet", 26.3356, -80.0702));
        boca.add(new MapMarkerDot("Lake Boca", 26.3436, -80.0743));
        MapLayer lhp = markerSet.add("Lighthouse Point");
        lhp.add(new MapMarkerDot("Hillsboro Inlet", 26.2580, -80.0812));
        
        BufferedImage iconSource = ImageUtils.loadResourceImage(Simulator.class, "/images/underwater-icons.png");
        
        boca.add(new MapMarkerImage(iconSource, 78, 78, 13, "Dive #1", 26.3356, -80.058));

        Application.mapPanel.setLayerRoot(markerSet);
    }

    
    
    
    /**
     * @param args Main program arguments
     */
    public static void main(String[] args) {
        
        // Construct a new DisplayWindow, which will set the "Application"
        // values...
        new DisplayWindow();
        
        // Load up this application's main menu...
        Simulator.initMainMenu();
        
        // Simulate the loading of "map markers"...
        Simulator.loadMapMarkers();
        
        // Create a control window to help us control the simulation...
        Simulator sim = new Simulator();
        
        // Make our windows visible...
        Application.displayWindow.setVisible(true);
        sim.setVisible(true);
        
    }
    
}
