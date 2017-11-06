package talkhub.com.br.th.Entities;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by ferna on 26/08/2017.
 */
public class Usuario {



    private String id;
    private String email;
    private String nome;
    private String sobrenome;
    private String nomeReferenciaUsuario;
    //Esta propriedade é usada para preencher listas quando se quer informar se o usuário é administrador ou não
    private Boolean administrador;
    //Esta propriedade é usada na pesquisa de usuários para inserir como membro, ela é necessária para preencher na
    //pesquisa somente usuários que ainda NÃO são membros
    private Boolean usuarioJaMembro;

    public Usuario() {
    }

    public Usuario(String email, String nome, String sobrenome, String nomeReferenciaUsuario, String keyUsuario) {
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nomeReferenciaUsuario = nomeReferenciaUsuario;
    }

    private DatabaseReference mRef;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeReferenciaUsuario() {
        return nomeReferenciaUsuario;
    }

    public Boolean getUsuarioJaMembro() {
        return usuarioJaMembro;
    }

    public void setUsuarioJaMembro(Boolean usuarioJaMembro) {
        this.usuarioJaMembro = usuarioJaMembro;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public void setNomeReferenciaUsuario(String companhia) {
        this.nomeReferenciaUsuario = companhia;
    }








}
