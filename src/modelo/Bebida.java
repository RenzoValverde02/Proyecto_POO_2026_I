package modelo;

public class Bebida extends Producto {
    
    // CONSTRUCTORES
    public Bebida(int idItem, String nombre, double precio) {
        super(idItem, nombre, precio);
        this.tipo = "BEBIDA";
    }

    // METODOS PROPIOS
    @Override
    public String obtenerDetalle() {
        return getNombre();
    }
}
