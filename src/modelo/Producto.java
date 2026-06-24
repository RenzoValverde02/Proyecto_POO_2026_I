package modelo;

public abstract class Producto {
    // VARIABLES
    private int idItem;
    private String nombre;
    private double precio;
    protected String tipo;

    // CONSTRUCTORES
    public Producto() {}

    public Producto(int idItem, String nombre, double precio) {
        this.idItem = idItem;
        this.nombre = nombre;
        this.precio = precio;
    }

    // METODOS 
    public abstract String obtenerDetalle();

    // METODOS 
    public int getIdItem() { return idItem; }
    public void setIdItem(int idItem) { this.idItem = idItem; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    
    public String getTipo() { return tipo; }

    // METODOS 
    @Override
    public String toString() {
        return nombre + " - S/ " + precio;
    }
}
