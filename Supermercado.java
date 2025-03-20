import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Supermercado {
    private JFrame frame;
    private JLabel lblNivel, lblPreco, lblPagamento, lblDesconto, lblMensagem;
    private JTextField txtTroco;
    private JButton btnConfirmar;
    private int nivel = 1, nivelMax;
    private double precoOriginal, desconto, pagamento, trocoCorreto;
    private Random random = new Random();

    public Supermercado (int dificuldade) {
        nivelMax = (dificuldade == 1) ? 20 : 30;
        criarInterface();
        proximoNivel(dificuldade);
    }

    private void criarInterface() {
        frame = new JFrame("Simulador de Caixa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 1));
        
        lblNivel = new JLabel("Nível: 1", SwingConstants.CENTER);
        lblPreco = new JLabel("", SwingConstants.CENTER);
        lblDesconto = new JLabel("", SwingConstants.CENTER);
        lblPagamento = new JLabel("", SwingConstants.CENTER);
        lblMensagem = new JLabel("", SwingConstants.CENTER);
        
        txtTroco = new JTextField();
        btnConfirmar = new JButton("Confirmar");
        
        btnConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarResposta();
            }
        });
        
        frame.add(lblNivel);
        frame.add(lblPreco);
        frame.add(lblDesconto);
        frame.add(lblPagamento);
        frame.add(txtTroco);
        frame.add(btnConfirmar);
        frame.add(lblMensagem);
        
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    private void proximoNivel(int dificuldade) {
        if (nivel > nivelMax) {
            lblMensagem.setText("Muito Bem! Jogo Finalizado!");
            btnConfirmar.setEnabled(false);
            return;
        }

        precoOriginal = Math.round((random.nextDouble() * 50 + 1) * 100.0) / 100.0;
        desconto = 0;
        if (dificuldade == 2 && nivel >= 5) {
            double percentualDesconto = Math.round((random.nextDouble() * 30) * 100.0) / 100.0;
            desconto = Math.round((precoOriginal * percentualDesconto / 100) * 100.0) / 100.0;
            lblDesconto.setText("Desconto: " + percentualDesconto + "%");
        } else {
            lblDesconto.setText("");
        }
        
        double precoFinal = precoOriginal - desconto;
        pagamento = Math.round((random.nextDouble() * 100 + precoFinal) * 100.0) / 100.0;
        trocoCorreto = Math.round((pagamento - precoFinal) * 100.0) / 100.0;
        
        lblNivel.setText("Nível: " + nivel);
        lblPreco.setText("Produto: R$" + precoOriginal);
        lblPagamento.setText("Pagamento: R$" + pagamento);
        txtTroco.setText("");
        lblMensagem.setText("");
    }

    private void verificarResposta() {
        try {
            double trocoUsuario = Double.parseDouble(txtTroco.getText());
            if (trocoUsuario == trocoCorreto) {
                lblMensagem.setText("Correto! Próximo nível.");
                nivel++;
                proximoNivel(nivelMax == 30 ? 2 : 1);
            } else {
                lblMensagem.setText("Errado! Troco correto: R$" + trocoCorreto);
                btnConfirmar.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            lblMensagem.setText("Digite um número válido!");
        }
    }

    public static void main(String[] args) {
        int dificuldade = JOptionPane.showOptionDialog(null, "Escolha a dificuldade:", "Dificuldade",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[]{"Normal", "Difícil"}, "Normal") + 1;
        new Supermercado (dificuldade);
    }
}
