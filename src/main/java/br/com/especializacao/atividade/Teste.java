package br.com.especializacao.atividade;

import br.com.especializacao.atividade.exceptions.VeicExistException;
import br.com.especializacao.atividade.exceptions.VelocException;

public class Teste {

    BDVeiculos bdVeiculos = new BDVeiculos();
    Leitura l = new Leitura();

    public static void main(String[] args) throws VeicExistException {
        Teste obj = new Teste();
        obj.menu();

    }

    public void menu() throws VeicExistException {

        while(true) {
            System.out.println("\nSistema de Gestão de Veículos - Menu Inicial");
            System.out.println("1 - Cadastrar Veículo de Passeio");
            System.out.println("2 - Cadastrar Veículo de Carga");
            System.out.println("3 - Imprimir Todos os Veículos de Passeio");
            System.out.println("4 - Imprimir Todos os Veículos de Carga");
            System.out.println("5 - Imprimir Veículo de Passeio pela Placa");
            System.out.println("6 - Imprimir Veículo de Carga pela Placa");
            System.out.println("7 - Excluir Veículo de Passeio pela Placa");
            System.out.println("8 - Excluir Veículo de Carga pela Placa");
            System.out.println("9 - Sair do Sistema");
            int opcao = Integer.parseInt(l.entDados("Digite a opção desejada: "));
            if (opcao == 1) {
                cadastrarVeiculoPasseio();
            } else if (opcao == 2) {
                cadastrarVeiculoCarga();
            } else if (opcao == 3) {
                listarVeiculosPasseio();
            } else if (opcao == 4) {
                listarVeiculosCarga();
            } else if (opcao == 5) {
                buscarVeiculoPasseioPelaPlaca();
            } else if (opcao == 6) {
                buscarVeiculoCargaPelaPlaca();
            } else if (opcao == 7) {
                excluirVeiculoPasseioPelaPlaca();
            } else if (opcao == 8) {
                excluirVeiculoCargaPelaPlaca();
            } else if (opcao == 9) {
                System.out.println("Encerrando o Sistema");
                break;
            } else {
                System.out.println("Opção inválida.\n");
            }
        }
    }

    public void cadastrarVeiculoPasseio() {
        String opcao = "sim";
        do {
            Passeio veicPasseio = new Passeio();
            System.out.println("Cadastro de Veículos de Passeio!");
            if (cadastrarVeiculo(veicPasseio, "passeio")){
                veicPasseio.setQtdPassageiros(Integer.parseInt(l.entDados("Informe a quantidade de passageiros: ")));
                bdVeiculos.setVeicPasseioList(veicPasseio);
                System.out.println("Veículo cadastrado com sucesso. ");
            }
            opcao = l.entDados("Deseja cadastrar outro veículo de passeio? Sim ou Nao ");
        } while (opcao.equalsIgnoreCase("sim"));
    }

    public void cadastrarVeiculoCarga() {
        String opcao = "sim";
        do {
            Carga veicCarga = new Carga();
            System.out.println("Cadastro de Veículos de Carga!");
            if(cadastrarVeiculo(veicCarga, "carga")) {
                veicCarga.setCargaMax(Integer.parseInt(l.entDados("Informe a carga máxima: ")));
                veicCarga.setTara(Integer.parseInt(l.entDados("Informe a tara: ")));
                bdVeiculos.setVeicCargaList(veicCarga);
                System.out.println("Veículo cadastrado com sucesso. ");
            }
            opcao = l.entDados("Deseja cadastrar outro veículo de passeio? Sim ou Nao ");
        } while (opcao.equalsIgnoreCase("sim"));

    }

    public boolean cadastrarVeiculo(Veiculo veiculo, String tipo) {
        veiculo.setPlaca(l.entDados("Informe a placa do veículos: "));
        try {
            bdVeiculos.veiculoPasseioExiste(veiculo.getPlaca());
            bdVeiculos.veiculoCargaExiste(veiculo.getPlaca());
            veiculo.setMarca(l.entDados("Informe a marca do veículos: "));
            veiculo.setModelo(l.entDados("Informe o modelo do veículos: "));
            veiculo.setCor(l.entDados("Informe a cor do veículos: "));
            veiculo.setQtdRodas(Integer.parseInt(l.entDados("Informe a quantidade de rodas do veículos: ")));
            veiculo.setVelocMax(Float.parseFloat(l.entDados("Informe a velocidade máxima do veículos: ")), tipo);
            veiculo.getMotor().setPotencia(Integer.parseInt(l.entDados("Informe a potência do motor do veículos: ")));
            veiculo.getMotor().setQtdPist(Integer.parseInt(l.entDados("Informe a quantidade de Pist do veículos: ")));
            return true;
        } catch (VeicExistException e) {
           return false;
        } catch (VelocException e) {
            veiculo.getMotor().setPotencia(Integer.parseInt(l.entDados("Informe a potência do motor do veículos: ")));
            veiculo.getMotor().setQtdPist(Integer.parseInt(l.entDados("Informe a quantidade de Pist do veículos: ")));
            return true;
        }
    }

    public void listarVeiculosPasseio() {
        for (Veiculo veiculo : bdVeiculos.getVeicPasseioList()) {
            System.out.println(veiculo.toString());
        }
    }

    public void listarVeiculosCarga() {
        for (Veiculo veiculo : bdVeiculos.getVeicCargaList()) {
            System.out.println(veiculo.toString());
        }
    }

    public void buscarVeiculoPasseioPelaPlaca() {
        String placa = l.entDados("Informe a placa do veículos que deseja buscar: ");
        Passeio veiculoLocalizado = bdVeiculos.getVeiculoPasseioPelaPlaca(placa);
        imprimirVeiculoLocalizadoOuErro(veiculoLocalizado, placa);
    }

    public void buscarVeiculoCargaPelaPlaca() {
        String placa = l.entDados("Informe a placa do veículos que deseja buscar: ");
        Carga veiculoLocalizado = bdVeiculos.getVeiculoCargaPelaPlaca(placa);
        imprimirVeiculoLocalizadoOuErro(veiculoLocalizado, placa);
    }

    public void imprimirVeiculoLocalizadoOuErro(Veiculo veiculoLocalizado, String placa){
        if(veiculoLocalizado != null){
            System.out.println(veiculoLocalizado.toString());
        } else {
            System.out.println("Veiculo não localizado pela Placa: " + placa);
        }
    }

    public void excluirVeiculoPasseioPelaPlaca() throws VeicExistException {
        try{
            String placa = l.entDados("Informe a placa do veículos que deseja buscar: ");
            bdVeiculos.excluirVeiculo(placa, "passeio");
            System.out.println("Veículo excluido com sucesso!");
        } catch (VeicExistException e){
        }
    }

    public void excluirVeiculoCargaPelaPlaca() throws VeicExistException {
        try{
            String placa = l.entDados("Informe a placa do veículos que deseja buscar: ");
            bdVeiculos.excluirVeiculo(placa, "carga");
            System.out.println("Veículo excluido com sucesso!");
        } catch (VeicExistException e){
        }
    }
}