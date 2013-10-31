import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class HexLayout implements LayoutManager {

    private int columns;
    private boolean startEvenRow;

    /**
     * Creates a hexagonal layout with a certain number of columns
     * @param columns
     */
    public HexLayout(int columns) {
        this.columns = columns;
    }

    /**
     * Creates a hexagonal layout with a certain number of columns with
     * ability to specify whether the first row is even or odd.
     * @param columns
     * @param startEvenRow
     */
    public HexLayout(int columns, Boolean startEvenRow) {
        this.columns = columns;
        this.startEvenRow = startEvenRow;
    }
    
    /**
     * Calculates the number of rows given number of columns and
     * starting even or odd row boolean.
     * 
     * @param parent
     * @return The number of rows
     */
    private Integer calculateRowCount(Container parent) {
        
        int rowCount = (this.startEvenRow) ? 1 : 0;
        int cells = parent.getComponents().length;
        
        while (cells > 0) {
            cells -= this.columns - (rowCount % 2);
            rowCount++;
        }
        
        // Fix row count since we started off at 1
        if (this.startEvenRow)
            rowCount--;
        
        return rowCount;
        
    }
    
    /**
     * Lays out components in desired hexagonal row pattern.
     */
    @Override
    public void layoutContainer(Container parent) {
        
        int rows = this.calculateRowCount(parent);
        
        // Segments horizontally = Full hexagon x2, offset hexagon x1
        int hexagonWidth = (parent.getWidth() / (this.columns*2 + this.columns-1)) * 2;
        
        // Segments vertically = # of rows + 1
        int hexagonHeight = (parent.getHeight() / (rows + 1)) * 2;

        int currentRow = 0;
        int currentColumn = 0;
        int iteration = (this.startEvenRow) ? 0 : 1;
        
        // Laying out each of the components in the container
        for (Component component : parent.getComponents()) {
            
            Boolean evenRow = (iteration % 2) == 0;
            int offsetX = 0;
            int offsetY = 0;
            
            // Calculate the x,y offets
            if (evenRow) {
                offsetX = (int)(hexagonWidth * 0.75) + currentColumn * hexagonWidth + currentColumn * (hexagonWidth / 2);
                offsetY = currentRow * (hexagonHeight / 2 - 6);
            } else {
                offsetX = currentColumn * hexagonWidth + currentColumn * (hexagonWidth / 2);
                offsetY = currentRow * (hexagonHeight / 2 - 6);
            }            
            component.setBounds(offsetX, offsetY, hexagonWidth, hexagonHeight);

            // Increment row and column counts
            currentColumn++;
            if ((evenRow && currentColumn == this.columns-1) || (!evenRow && currentColumn == this.columns)) {
                currentColumn = 0;
                currentRow++;
                iteration++;
            }
            
        }

    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(800, 600);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(800, 600);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // TODO Auto-generated method stub
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        // TODO Auto-generated method stub
    }

}