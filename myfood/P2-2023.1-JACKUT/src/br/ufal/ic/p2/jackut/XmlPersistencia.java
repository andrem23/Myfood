package br.ufal.ic.p2.jackut;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlPersistencia {

    // Método para salvar os usuários em um arquivo XML
    public static void salvarUsuarios(Map<String, Usuario> usuarios, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Usuarios");
            doc.appendChild(rootElement);

            for (Map.Entry<String, Usuario> entry : usuarios.entrySet()) {
                Usuario usuario = entry.getValue();
                Element usuarioElement = doc.createElement("Usuario");
                rootElement.appendChild(usuarioElement);

                Element idElement = doc.createElement("Id");
                idElement.appendChild(doc.createTextNode(usuario.getId()));
                usuarioElement.appendChild(idElement);

                Element nomeElement = doc.createElement("Nome");
                nomeElement.appendChild(doc.createTextNode(usuario.getNome()));
                usuarioElement.appendChild(nomeElement);

                Element emailElement = doc.createElement("Email");
                emailElement.appendChild(doc.createTextNode(usuario.getEmail()));
                usuarioElement.appendChild(emailElement);

                Element senhaElement = doc.createElement("Senha");
                senhaElement.appendChild(doc.createTextNode(usuario.getSenha()));
                usuarioElement.appendChild(senhaElement);

                if (usuario instanceof Cliente) {
                    Cliente cliente = (Cliente) usuario;
                    Element enderecoElement = doc.createElement("Endereco");
                    enderecoElement.appendChild(doc.createTextNode(cliente.getEndereco()));
                    usuarioElement.appendChild(enderecoElement);
                }

                if (usuario instanceof DonoEstabelecimento) {
                    DonoEstabelecimento dono = (DonoEstabelecimento) usuario;
                    Element cpfElement = doc.createElement("Cpf");
                    cpfElement.appendChild(doc.createTextNode(dono.getCpf()));
                    usuarioElement.appendChild(cpfElement);

                    Element enderecoElement = doc.createElement("Endereco");
                    enderecoElement.appendChild(doc.createTextNode(dono.getEndereco()));
                    usuarioElement.appendChild(enderecoElement);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para carregar os usuários de um arquivo XML
    public static Map<String, Usuario> carregarUsuarios(String filePath) {
        Map<String, Usuario> usuarios = new HashMap<>();

        try {
            File file = new File(filePath);

            // Verifica se o arquivo existe e não está vazio
            if (file.exists() && file.length() > 0) {
                // Limpar os dados em memória antes de carregar novos
                usuarios.clear();

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList usuarioList = doc.getElementsByTagName("Usuario");

                for (int i = 0; i < usuarioList.getLength(); i++) {
                    Element usuarioElement = (Element) usuarioList.item(i);

                    String id = usuarioElement.getElementsByTagName("Id").item(0).getTextContent();
                    String nome = usuarioElement.getElementsByTagName("Nome").item(0).getTextContent();
                    String email = usuarioElement.getElementsByTagName("Email").item(0).getTextContent();
                    String senha = usuarioElement.getElementsByTagName("Senha").item(0).getTextContent();

                    Usuario usuario;
                    if (usuarioElement.getElementsByTagName("Cpf").getLength() > 0) {
                        String cpf = usuarioElement.getElementsByTagName("Cpf").item(0).getTextContent();
                        String endereco = usuarioElement.getElementsByTagName("Endereco").item(0).getTextContent();
                        usuario = new DonoEstabelecimento(id, nome, email, senha, cpf, endereco);
                    } else {
                        String endereco = usuarioElement.getElementsByTagName("Endereco").item(0).getTextContent();
                        usuario = new Cliente(id, nome, email, senha, endereco);
                    }

                    usuarios.put(id, usuario);
                }

                // Limpar o arquivo após o carregamento
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    // salvar Empresas
    public static void salvarEmpresas(Map<String, Empresa> empresas, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Empresas");
            doc.appendChild(rootElement);

            for (Map.Entry<String, Empresa> entry : empresas.entrySet()) {
                Empresa empresa = entry.getValue();
                Element empresaElement = doc.createElement("Empresa");
                rootElement.appendChild(empresaElement);

                Element idElement = doc.createElement("Id");
                idElement.appendChild(doc.createTextNode(empresa.getId()));
                empresaElement.appendChild(idElement);

                Element nomeElement = doc.createElement("Nome");
                nomeElement.appendChild(doc.createTextNode(empresa.getNome()));
                empresaElement.appendChild(nomeElement);

                Element donoIDElement = doc.createElement("DonoID");
                donoIDElement.appendChild(doc.createTextNode(String.valueOf(empresa.getDonoID())));
                empresaElement.appendChild(donoIDElement);

                Element enderecoElement = doc.createElement("Endereco");
                enderecoElement.appendChild(doc.createTextNode(empresa.getEndereco()));
                empresaElement.appendChild(enderecoElement);

                Element tipoEmpresaElement = doc.createElement("TipoEmpresa");
                tipoEmpresaElement.appendChild(doc.createTextNode(empresa.getTipoEmpresa()));
                empresaElement.appendChild(tipoEmpresaElement);

                if (empresa instanceof Restaurante) {
                    Restaurante restaurante = (Restaurante) empresa;
                    Element tipoCozinhaElement = doc.createElement("TipoCozinha");
                    tipoCozinhaElement.appendChild(doc.createTextNode(restaurante.getTipoCozinha()));
                    empresaElement.appendChild(tipoCozinhaElement);
                }

                /* Para Proximas implementações */
                else if (empresa instanceof Farmacia) {
                    Farmacia farmacia = (Farmacia) empresa;
                } else if (empresa instanceof Mercado) {
                    Mercado mercado = (Mercado) empresa;
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para carregar as empresas de um arquivo XML
    public static Map<String, Empresa> carregarEmpresas(String filePath) {
        Map<String, Empresa> empresas = new HashMap<>();

        try {
            File file = new File(filePath);

            // Verifica se o arquivo existe e não está vazio
            if (file.exists() && file.length() > 0) {
                // Limpar os dados em memória antes de carregar novos
                empresas.clear();

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList empresaList = doc.getElementsByTagName("Empresa");

                for (int i = 0; i < empresaList.getLength(); i++) {
                    Element empresaElement = (Element) empresaList.item(i);

                    String id = empresaElement.getElementsByTagName("Id").item(0).getTextContent();
                    String nome = empresaElement.getElementsByTagName("Nome").item(0).getTextContent();
                    String donoID = empresaElement.getElementsByTagName("DonoID").item(0).getTextContent();
                    String endereco = empresaElement.getElementsByTagName("Endereco").item(0).getTextContent();
                    String tipoEmpresa = empresaElement.getElementsByTagName("TipoEmpresa").item(0).getTextContent();

                    Empresa empresa;

                    if (tipoEmpresa.equals("restaurante")) {
                        String tipoCozinha = empresaElement.getElementsByTagName("TipoCozinha").item(0)
                                .getTextContent();
                        empresa = new Restaurante(id, nome, donoID, endereco, tipoEmpresa, tipoCozinha);
                    }
                    /* para proximas implementaçoes */
                    else if (tipoEmpresa.equals("Mercado")) {

                        empresa = new Mercado(id, nome, donoID, endereco, tipoEmpresa);
                    } else if (tipoEmpresa.equals("Farmacia")) {
                        empresa = new Farmacia(id, nome, donoID, endereco, tipoEmpresa);
                    } else {
                        throw new IllegalArgumentException("Tipo de empresa desconhecido: " + tipoEmpresa);
                    }

                    empresas.put(id, empresa);
                }

                // Limpar o arquivo após o carregamento
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return empresas;
    }

    // Método para salvar os produtos em um arquivo XML
    public static void salvarProdutos(Map<String, Produto> produtos, String filePath) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Produtos");
            doc.appendChild(rootElement);

            for (Map.Entry<String, Produto> entry : produtos.entrySet()) {
                Produto produto = entry.getValue();
                Element produtoElement = doc.createElement("Produto");
                rootElement.appendChild(produtoElement);

                Element idElement = doc.createElement("Id");
                idElement.appendChild(doc.createTextNode(produto.getId()));
                produtoElement.appendChild(idElement);

                Element empresaIDElement = doc.createElement("EmpresaID");
                empresaIDElement.appendChild(doc.createTextNode(produto.getEmpresaID()));
                produtoElement.appendChild(empresaIDElement);

                Element nomeElement = doc.createElement("Nome");
                nomeElement.appendChild(doc.createTextNode(produto.getNome()));
                produtoElement.appendChild(nomeElement);

                Element valorElement = doc.createElement("Valor");
                valorElement.appendChild(doc.createTextNode(String.valueOf(produto.getValor())));
                produtoElement.appendChild(valorElement);

                Element categoriaElement = doc.createElement("Categoria");
                categoriaElement.appendChild(doc.createTextNode(produto.getCategoria()));
                produtoElement.appendChild(categoriaElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para carregar os produtos de um arquivo XML
    public static Map<String, Produto> carregarProdutos(String filePath) {
        Map<String, Produto> produtos = new HashMap<>();

        try {
            File file = new File(filePath);

            // Verifica se o arquivo existe e não está vazio
            if (file.exists() && file.length() > 0) {
                // Limpar os dados em memória antes de carregar novos
                produtos.clear();

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList produtoList = doc.getElementsByTagName("Produto");

                for (int i = 0; i < produtoList.getLength(); i++) {
                    Element produtoElement = (Element) produtoList.item(i);

                    String id = produtoElement.getElementsByTagName("Id").item(0).getTextContent();
                    String empresaID = produtoElement.getElementsByTagName("EmpresaID").item(0).getTextContent();
                    String nome = produtoElement.getElementsByTagName("Nome").item(0).getTextContent();
                    float valor = Float
                            .parseFloat(produtoElement.getElementsByTagName("Valor").item(0).getTextContent());
                    String categoria = produtoElement.getElementsByTagName("Categoria").item(0).getTextContent();

                    Produto produto = new Produto(id, empresaID, nome, valor, categoria);
                    produtos.put(id, produto);
                }

                // Limpar o arquivo após o carregamento
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return produtos;
    }

    // salvar pedido ---------------------------------------------------------

    public static void salvarPedidos(Map<String, Pedido> pedidos, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Pedidos");
            doc.appendChild(rootElement);

            for (Pedido pedido : pedidos.values()) {
                Element pedidoElement = doc.createElement("Pedido");
                pedidoElement.setAttribute("id", pedido.getPedidoID());

                Element clienteIDElement = doc.createElement("ClienteID");
                clienteIDElement.appendChild(doc.createTextNode(pedido.getClienteID()));
                pedidoElement.appendChild(clienteIDElement);

                Element empresaIDElement = doc.createElement("EmpresaID");
                empresaIDElement.appendChild(doc.createTextNode(pedido.getEmpresaID()));
                pedidoElement.appendChild(empresaIDElement);

                Element estadoElement = doc.createElement("Estado");
                estadoElement.appendChild(doc.createTextNode(pedido.getEstado()));
                pedidoElement.appendChild(estadoElement);

                Element valorElement = doc.createElement("Valor");
                valorElement.appendChild(doc.createTextNode(String.valueOf(pedido.getValor())));
                pedidoElement.appendChild(valorElement);

                Element produtosElement = doc.createElement("Produtos");
                for (Produto produto : pedido.getProdutos()) {
                    Element produtoElement = doc.createElement("Produto");
                    produtoElement.setAttribute("id", produto.getId());

                    Element nomeElement = doc.createElement("Nome");
                    nomeElement.appendChild(doc.createTextNode(produto.getNome()));
                    produtoElement.appendChild(nomeElement);

                    Element valorProdutoElement = doc.createElement("Valor");
                    valorProdutoElement.appendChild(doc.createTextNode(String.valueOf(produto.getValor())));
                    produtoElement.appendChild(valorProdutoElement);

                    Element categoriaElement = doc.createElement("Categoria");
                    categoriaElement.appendChild(doc.createTextNode(produto.getCategoria()));
                    produtoElement.appendChild(categoriaElement);

                    produtosElement.appendChild(produtoElement);
                }
                pedidoElement.appendChild(produtosElement);

                rootElement.appendChild(pedidoElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // carregar pedido ----------------------
    public static Map<String, Pedido> carregarPedidos(String filePath) {
        Map<String, Pedido> pedidos = new HashMap<>();

        try {
            File file = new File(filePath);

            // Verifica se o arquivo existe e não está vazio
            if (file.exists() && file.length() > 0) {
                // Limpar os dados em memória antes de carregar novos
                pedidos.clear();

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList pedidoList = doc.getElementsByTagName("Pedido");

                for (int i = 0; i < pedidoList.getLength(); i++) {
                    Element pedidoElement = (Element) pedidoList.item(i);
                    String pedidoID = pedidoElement.getAttribute("id");
                    String clienteID = pedidoElement.getElementsByTagName("ClienteID").item(0).getTextContent();
                    String empresaID = pedidoElement.getElementsByTagName("EmpresaID").item(0).getTextContent();
                    String estado = pedidoElement.getElementsByTagName("Estado").item(0).getTextContent();
                    float valor = Float
                            .parseFloat(pedidoElement.getElementsByTagName("Valor").item(0).getTextContent());

                    NodeList produtoList = pedidoElement.getElementsByTagName("Produto");
                    List<Produto> produtos = new ArrayList<>();
                    for (int j = 0; j < produtoList.getLength(); j++) {
                        Element produtoElement = (Element) produtoList.item(j);
                        String produtoID = produtoElement.getAttribute("id");
                        String nome = produtoElement.getElementsByTagName("Nome").item(0).getTextContent();
                        float valorProduto = Float
                                .parseFloat(produtoElement.getElementsByTagName("Valor").item(0).getTextContent());
                        String categoria = produtoElement.getElementsByTagName("Categoria").item(0).getTextContent();

                        Produto produto = new Produto(produtoID, empresaID, nome, valorProduto, categoria);
                        produtos.add(produto);
                    }

                    Pedido pedido = new Pedido(pedidoID, clienteID, empresaID, estado, produtos, valor);
                    pedidos.put(pedidoID, pedido);
                }

                // Limpar o arquivo após o carregamento
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidos;

    }

}