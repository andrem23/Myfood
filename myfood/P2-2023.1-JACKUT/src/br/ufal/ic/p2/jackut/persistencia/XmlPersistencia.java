package br.ufal.ic.p2.jackut.persistencia;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import br.ufal.ic.p2.jackut.empresa.Empresa;
import br.ufal.ic.p2.jackut.empresa.Farmacia;
import br.ufal.ic.p2.jackut.empresa.Mercado;
import br.ufal.ic.p2.jackut.empresa.Restaurante;
import br.ufal.ic.p2.jackut.entrega.Entrega;
import br.ufal.ic.p2.jackut.pedido.Pedido;
import br.ufal.ic.p2.jackut.produto.Produto;
import br.ufal.ic.p2.jackut.usuario.Cliente;
import br.ufal.ic.p2.jackut.usuario.DonoEstabelecimento;
import br.ufal.ic.p2.jackut.usuario.Entregador;
import br.ufal.ic.p2.jackut.usuario.Usuario;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XmlPersistencia {

    // Função auxiliar para obter o valor de um nó
    private static String getNodeValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node != null ? node.getTextContent() : null;
        }
        return null;
    }

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

                else if (usuario instanceof DonoEstabelecimento) {
                    DonoEstabelecimento dono = (DonoEstabelecimento) usuario;
                    Element cpfElement = doc.createElement("Cpf");
                    cpfElement.appendChild(doc.createTextNode(dono.getCpf()));
                    usuarioElement.appendChild(cpfElement);

                    Element enderecoElement = doc.createElement("Endereco");
                    enderecoElement.appendChild(doc.createTextNode(dono.getEndereco()));
                    usuarioElement.appendChild(enderecoElement);
                }
                 else if (usuario instanceof Entregador) {
                Entregador entregador = (Entregador) usuario;
                Element enderecoElement = doc.createElement("Endereco");
                enderecoElement.appendChild(doc.createTextNode(entregador.getEndereco()));
                usuarioElement.appendChild(enderecoElement);

                Element veiculoElement = doc.createElement("Veiculo");
                veiculoElement.appendChild(doc.createTextNode(entregador.getVeiculo()));
                usuarioElement.appendChild(veiculoElement);

                Element placaElement = doc.createElement("Placa");
                placaElement.appendChild(doc.createTextNode(entregador.getPlaca()));
                usuarioElement.appendChild(placaElement);
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
        Map<String, Usuario> usuarios = new LinkedHashMap<>();

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

                    String id = getNodeValue(usuarioElement, "Id");
                    String nome = getNodeValue(usuarioElement, "Nome");
                    String email = getNodeValue(usuarioElement, "Email");
                    String senha = getNodeValue(usuarioElement, "Senha");

                    Usuario usuario;
                    if (usuarioElement.getElementsByTagName("Cpf").getLength() > 0) {
                        String cpf = getNodeValue(usuarioElement, "Cpf");
                        String endereco = getNodeValue(usuarioElement, "Endereco");
                        usuario = new DonoEstabelecimento(id, nome, email, senha, cpf, endereco);
                    } 
                    else if (usuarioElement.getElementsByTagName("Veiculo").getLength() > 0) {
                        String endereco = getNodeValue(usuarioElement, "Endereco");
                        String veiculo = getNodeValue(usuarioElement, "Veiculo");
                        String placa = getNodeValue(usuarioElement, "Placa");
                        usuario = new Entregador(id, nome, email, senha, endereco, veiculo, placa);
                    }
                    else {
                        String endereco = getNodeValue(usuarioElement, "Endereco");
                        usuario = new Cliente(id, nome, email, senha, endereco);
                    }

                    usuarios.put(id, usuario);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
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

                else if (empresa instanceof Mercado) {
                    Mercado mercado = (Mercado) empresa;
                    Element tipoMercadoElement = doc.createElement("TipoMercado");
                    tipoMercadoElement.appendChild(doc.createTextNode(mercado.getTipoMercado()));
                    empresaElement.appendChild(tipoMercadoElement);

                    Element abreElement = doc.createElement("Abre");
                    abreElement.appendChild(doc.createTextNode(mercado.getAbre()));
                    empresaElement.appendChild(abreElement);

                    Element fechaElement = doc.createElement("Fecha");
                    fechaElement.appendChild(doc.createTextNode(mercado.getFecha()));
                    empresaElement.appendChild(fechaElement);
                }
                // Implementação para Farmacia
                else if (empresa instanceof Farmacia) {
                    Farmacia farmacia = (Farmacia) empresa;
                    Element aberto24HorasElement = doc.createElement("Aberto24Horas");
                    aberto24HorasElement.appendChild(doc.createTextNode(String.valueOf(farmacia.getAberto24Horas())));
                    empresaElement.appendChild(aberto24HorasElement);

                    Element numeroFuncionariosElement = doc.createElement("NumeroFuncionarios");
                    numeroFuncionariosElement
                            .appendChild(doc.createTextNode(String.valueOf(farmacia.getNumeroFuncionarios())));
                    empresaElement.appendChild(numeroFuncionariosElement);
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
        Map<String, Empresa> empresas = new LinkedHashMap<>();

        try {
            File file = new File(filePath);

            if (file.exists() && file.length() > 0) {

                empresas.clear();

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList empresaList = doc.getElementsByTagName("Empresa");

                for (int i = 0; i < empresaList.getLength(); i++) {
                    Element empresaElement = (Element) empresaList.item(i);

                    String id = getNodeValue(empresaElement, "Id");
                    String nome = getNodeValue(empresaElement, "Nome");
                    String donoID = getNodeValue(empresaElement, "DonoID");
                    String endereco = getNodeValue(empresaElement, "Endereco");
                    String tipoEmpresa = getNodeValue(empresaElement, "TipoEmpresa");

                    Empresa empresa;

                    if (tipoEmpresa.equals("restaurante")) {
                        String tipoCozinha = getNodeValue(empresaElement, "TipoCozinha");
                        empresa = new Restaurante(id, nome, donoID, endereco, tipoEmpresa, tipoCozinha);
                    } else if (tipoEmpresa.equals("mercado")) {
                        String tipoMercado = getNodeValue(empresaElement, "TipoMercado");
                        String abre = getNodeValue(empresaElement, "Abre");
                        String fecha = getNodeValue(empresaElement, "Fecha");
                        empresa = new Mercado(id, nome, donoID, endereco, tipoEmpresa, abre, fecha, tipoMercado);
                    } else if (tipoEmpresa.equals("farmacia")) {
                        boolean aberto24Horas = Boolean.parseBoolean(getNodeValue(empresaElement, "Aberto24Horas"));
                        int numeroFuncionarios = Integer.parseInt(getNodeValue(empresaElement, "NumeroFuncionarios"));
                        empresa = new Farmacia(id, nome, donoID, endereco, tipoEmpresa, aberto24Horas,
                                numeroFuncionarios);
                    } else {
                        throw new IllegalArgumentException("Tipo de empresa desconhecido: " + tipoEmpresa);
                    }

                    empresas.put(id, empresa);
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
        Map<String, Produto> produtos = new LinkedHashMap<>();

        try {
            File file = new File(filePath);
            if (file.exists() && file.length() > 0) {
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
        Map<String, Pedido> pedidos = new LinkedHashMap<>();

        try {
            File file = new File(filePath);

            if (file.exists() && file.length() > 0) {
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

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidos;

    }

    public static void salvarEntregas(Map<String, Entrega> entregas, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Entregas");
            doc.appendChild(rootElement);

            for (Map.Entry<String, Entrega> entry : entregas.entrySet()) {
                Entrega entrega = entry.getValue();
                Element entregaElement = doc.createElement("Entrega");
                rootElement.appendChild(entregaElement);

                Element idElement = doc.createElement("Id");
                idElement.appendChild(doc.createTextNode(entrega.getIdEntrega()));
                entregaElement.appendChild(idElement);

                Element clienteIDElement = doc.createElement("ClienteID");
                clienteIDElement.appendChild(doc.createTextNode(entrega.getNomeCliente()));
                entregaElement.appendChild(clienteIDElement);

                Element empresaIDElement = doc.createElement("EmpresaID");
                empresaIDElement.appendChild(doc.createTextNode(entrega.getNomeEmpresa()));
                entregaElement.appendChild(empresaIDElement);

                Element pedidoIDElement = doc.createElement("PedidoID");
                pedidoIDElement.appendChild(doc.createTextNode(entrega.getPedidoID()));
                entregaElement.appendChild(pedidoIDElement);

                Element entregadorIDElement = doc.createElement("EntregadorID");
                entregadorIDElement.appendChild(doc.createTextNode(entrega.getEntregadorID()));
                entregaElement.appendChild(entregadorIDElement);

                Element destinoElement = doc.createElement("Destino");
                destinoElement.appendChild(doc.createTextNode(entrega.getDestino()));
                entregaElement.appendChild(destinoElement);

                // Salvar a lista de produtos com detalhes de cada um
                Element produtosElement = doc.createElement("Produtos");
                for (Produto produto : entrega.getProdutos()) {
                    Element produtoElement = doc.createElement("Produto");

                    Element produtoIdElement = doc.createElement("Id");
                    produtoIdElement.appendChild(doc.createTextNode(produto.getId()));
                    produtoElement.appendChild(produtoIdElement);

                    Element nomeElement = doc.createElement("Nome");
                    nomeElement.appendChild(doc.createTextNode(produto.getNome()));
                    produtoElement.appendChild(nomeElement);

                    Element valorElement = doc.createElement("Valor");
                    valorElement.appendChild(doc.createTextNode(String.valueOf(produto.getValor())));
                    produtoElement.appendChild(valorElement);

                    Element categoriaElement = doc.createElement("Categoria");
                    categoriaElement.appendChild(doc.createTextNode(produto.getCategoria()));
                    produtoElement.appendChild(categoriaElement);

                    Element empresaIdElement = doc.createElement("EmpresaID");
                    empresaIdElement.appendChild(doc.createTextNode(produto.getEmpresaID()));
                    produtoElement.appendChild(empresaIdElement);

                    produtosElement.appendChild(produtoElement);
                }
                entregaElement.appendChild(produtosElement);
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

    // Método para carregar as entregas de um arquivo XML
    public static Map<String, Entrega> carregarEntregas(String filePath) {
        Map<String, Entrega> entregas = new LinkedHashMap<>();

        try {
            File file = new File(filePath);

            if (file.exists() && file.length() > 0) {
                entregas.clear();

                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(file);
                doc.getDocumentElement().normalize();

                NodeList entregaList = doc.getElementsByTagName("Entrega");

                for (int i = 0; i < entregaList.getLength(); i++) {
                    Element entregaElement = (Element) entregaList.item(i);

                    String id = entregaElement.getElementsByTagName("Id").item(0).getTextContent();
                    String clienteID = entregaElement.getElementsByTagName("ClienteID").item(0).getTextContent();
                    String empresaID = entregaElement.getElementsByTagName("EmpresaID").item(0).getTextContent();
                    String pedidoID = entregaElement.getElementsByTagName("PedidoID").item(0).getTextContent();
                    String entregadorID = entregaElement.getElementsByTagName("EntregadorID").item(0).getTextContent();
                    String destino = entregaElement.getElementsByTagName("Destino").item(0).getTextContent();

                    // Obtém a lista de produtos
                    List<Produto> produtos = new ArrayList<>();
                    NodeList produtoList = entregaElement.getElementsByTagName("Produto");
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

                    Entrega entrega = new Entrega(id, clienteID, empresaID, pedidoID, entregadorID, destino, null);
                    entregas.put(id, entrega);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return entregas;
    }

}