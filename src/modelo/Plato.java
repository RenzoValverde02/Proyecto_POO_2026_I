package modelo;

public class Plato extends Producto {
    
    // CONSTRUCTORES
    public Plato(int idItem, String nombre, double precio) {
        super(idItem, nombre, precio);
        this.tipo = "PLATO";
    }

    // METODOS 
    @Override
    public String obtenerDetalle() {
    	return getNombre();
    }
}
