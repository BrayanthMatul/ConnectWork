package models;

import enums.TipoUsuario;

public class Usuario {
    
    private int id;
    private String username;
    private String password;
    private String nombre_completo;
    private String email;
    private TipoUsuario tipo_usuario;

    public Usuario(int id, String username, String password, String nombre_completo, String email, TipoUsuario tipo_usuario) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombre_completo = nombre_completo;
        this.email = email;
        this.tipo_usuario = tipo_usuario;
    }

    public Usuario(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        this.nombre_completo = usuario.getNombre_completo();
        this.email = usuario.getEmail();
        this.tipo_usuario = usuario.getTipo_usuario();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public String getEmail() {
        return email;
    }

    public TipoUsuario getTipo_usuario() {
        return tipo_usuario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo_usuario(TipoUsuario tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

}
