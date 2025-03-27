let nivel = 1;
let precoOriginal = 0;
let desconto = 0;
let precoFinal = 0;
let pagamento = 0;
const dificuldade = "dificil";

// Carregar nível salvo manualmente
if (localStorage.getItem("nivelSalvo")) {
    nivel = parseInt(localStorage.getItem("nivelSalvo"));
}

function iniciarJogo(continuar) {
    if (!continuar) {
        nivel = 1;
        localStorage.removeItem("nivelSalvo"); // Se for novo jogo, apagar progresso salvo
    } else {
        if (localStorage.getItem("nivelSalvo")) {
            nivel = parseInt(localStorage.getItem("nivelSalvo"));
        } else {
            alert("Nenhum jogo salvo encontrado! Começando do nível 1.");
            nivel = 1;
        }
    }

    document.getElementById("menu").style.display = "none";
    document.getElementById("jogo").style.display = "block";
    gerarNovaRodada();
}

function gerarNovaRodada() {
    precoOriginal = (Math.random() * 50 + 1).toFixed(2);
    desconto = 0;

    // No modo difícil, aplicar desconto a partir do nível 5
    if (dificuldade === "dificil" && nivel >= 5) {
        let porcentagemDesconto = (Math.random() * 30 + 1).toFixed(0); // Até 30%
        desconto = ((precoOriginal * porcentagemDesconto) / 100).toFixed(2);
        document.getElementById("desconto").innerText = `Desconto: ${porcentagemDesconto}% (Calcule o novo preço!)`;
    } else {
        document.getElementById("desconto").innerText = "";
    }

    precoFinal = (precoOriginal - desconto).toFixed(2);
    pagamento = (Math.random() * 100 + parseFloat(precoFinal)).toFixed(2);

    document.getElementById("nivel").innerText = `🛍️ Nível ${nivel}`;
    document.getElementById("produto").innerText = `Produto: R$${precoOriginal}`;
    document.getElementById("pagamento").innerText = `Cliente pagou: R$${pagamento}`;
    document.getElementById("mensagem").innerText = "";
    document.getElementById("trocoInput").value = "";
}

function verificarTroco() {
    let trocoUsuario = parseFloat(document.getElementById("trocoInput").value);
    let trocoCorreto = (pagamento - precoFinal).toFixed(2);

    if (trocoUsuario == trocoCorreto) {
        document.getElementById("mensagem").innerText = "✅ Correto! Avançando de nível...";
        nivel++;
        setTimeout(gerarNovaRodada, 1000);
    } else {
        document.getElementById("mensagem").innerText = `❌ Errado! O troco correto era R$${trocoCorreto}`;
    }
}

// Função para salvar o progresso manualmente
function salvarJogo() {
    localStorage.setItem("nivelSalvo", nivel);
    alert(`✅ Jogo salvo! Você pode continuar do nível ${nivel} na próxima vez.`);
}
