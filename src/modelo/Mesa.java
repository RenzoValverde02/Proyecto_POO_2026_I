package modelo;

public class Mesa {
    // VARIABLES
    private int idMesa;
    private String numeroMesa;
    private int capacidad;
    private String estado;

    // CONSTRUCTORES
    public Mesa(int idMesa, String numeroMesa, int capacidad, String estado) {
        this.idMesa = idMesa;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    // METODOS GET Y SET
    public int getIdMesa() { return idMesa; }
    public String getNumeroMesa() { return numeroMesa; }
    public int getCapacidad() { return capacidad; }
    public String getEstado() { return estado; }

    // METODOS 
    @Override
    public String toString() {
        return numeroMesa + " - " + capacidad + " personas";
    }
}
