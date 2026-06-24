package modelo;

public class Empleado extends Usuario {
    
    // VARIABLES
    private String turno;

    // CONSTRUCTORES
    public Empleado() {
        super();
    }

    public Empleado(String nombre, String apellido, String rol, String turno) {
        super();
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setRol(rol);
        this.turno = turno;
    }

    // METODOS
    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    // SOBRESCRITURA DEL MÉTODO ABSTRACTO (Polimorfismo)
    @Override
    public String getDescripcionRol() {
        return "Empleado Operativo - Rol asignado: " + this.rol;
    }
}
