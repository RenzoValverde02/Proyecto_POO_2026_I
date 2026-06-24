package modelo;

public abstract class Usuario {
    // VARIABLES
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String usuario;
    private String contrasena;
    protected String rol;
    private int estado;

    // CONSTRUCTORES
    public Usuario() {}

    // MÉTODO ABSTRACTO (Polimorfismo)
    public abstract String getDescripcionRol();

    // METODOS 
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
}
