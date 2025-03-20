import java.util.Random;
import java.util.Scanner;

class JogoSupermercado {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int nivel = 1;
        boolean jogando = true;

        System.out.println("Bem-vindo ao Simulador de Caixa do Supermercado!");
        System.out.println("Escolha a dificuldade: \n1 - Normal (até nível 20) \n2 - Difícil (até nível 30, desafios a partir do nível 5)");
        int dificuldade = scanner.nextInt();

        int nivelMaximo = (dificuldade == 1) ? 20 : 30;

        while (jogando && nivel <= nivelMaximo) {
            double precoOriginal = Math.round((random.nextDouble() * 50 + 1) * 100.0) / 100.0;
            double desconto = 0;
            double percentualDesconto = 0;

            if (dificuldade == 2 && nivel >= 5) {
                percentualDesconto = Math.round((random.nextDouble() * 30) * 100.0) / 100.0;
                desconto = Math.round((precoOriginal * percentualDesconto / 100) * 100.0) / 100.0;
            }

            double precoFinal = precoOriginal - desconto;
            double pagamento = Math.round((random.nextDouble() * 100 + precoFinal) * 100.0) / 100.0;
            double trocoCorreto = Math.round((pagamento - precoFinal) * 100.0) / 100.0;

            System.out.println("\n Nível " + nivel);
            System.out.println("Produto: R$" + precoOriginal);
            if (desconto > 0) {
                System.out.println("Desconto de " + percentualDesconto + "% será aplicado. Calcule o novo preço!");
            }
            System.out.println("Cliente pagou: R$" + pagamento);
            System.out.print("Quanto é o troco? R$");
            double trocoUsuario = scanner.nextDouble();

            if (trocoUsuario == trocoCorreto) {
                System.out.println("Correto! Você avançou para o próximo nível!");
                nivel++;
            } else {
                System.out.println("Errado! O troco correto era R$" + trocoCorreto);
                System.out.println("Game Over! Você chegou até o nível " + nivel);
                jogando = false;
            }
        }

        System.out.println("Parabéns! Você completou o modo " + (dificuldade == 1 ? "Normal" : "Difícil") + "!");
        scanner.close();
    }
}
