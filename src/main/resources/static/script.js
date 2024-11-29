/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


// Função para alternar a exibição do sidebar
function toggleCart() {
    const cartSidebar = document.getElementById('cartSidebar');
    cartSidebar.classList.toggle('sidebar-open');
    console.log("working");
}


let scrollPosition = 0;

function scrollCarousel(direction) {
  const carousel = document.querySelector(".carouselshop");
  const itemWidth = document.querySelector(".carouselshop-item").offsetWidth + 10; // Largura + gap

  // Atualiza a posição de scroll
  scrollPosition += direction * itemWidth * 2; // Move 5 itens por vez
  const maxScroll = carousel.scrollWidth - carousel.clientWidth + 250;

  // Impede que passe do limite
  if (scrollPosition < 0) scrollPosition = 0;
  if (scrollPosition > maxScroll) scrollPosition = maxScroll;

  // Aplica a transição
  carousel.style.transform = `translateX(-${scrollPosition}px)`;
}

function updateQuantity(button, change) {
  const quantitySpan = button.parentElement.querySelector("span");
  let quantity = parseInt(quantitySpan.textContent);

  // Atualiza o valor, garantindo que não seja negativo
  quantity = Math.max(0, quantity + change);
  quantitySpan.textContent = quantity;
}


function toggleFaq(faqItem) {
    const answer = faqItem.nextElementSibling;
    const seta = faqItem.querySelector('.seta');

    if (answer.style.display === 'block') {
        answer.style.display = 'none';
        seta.textContent = '➤';
    } else {
        answer.style.display = 'block';
        seta.textContent = '▼';
    }
}

function smoothScroll(target, duration) {
    const targetPosition = target.getBoundingClientRect().top + window.scrollY - (window.innerHeight / 2 - target.offsetHeight / 2); 
    const startPosition = window.pageYOffset;
    const distance = targetPosition - startPosition;
    let startTime = null;

    function animationScroll(currentTime) {
        if (startTime === null) startTime = currentTime;
        const timeElapsed = currentTime - startTime;
        const run = ease(timeElapsed, startPosition, distance, duration);
        window.scrollTo(0, run);
        if (timeElapsed < duration) requestAnimationFrame(animationScroll);
    }

    function ease(t, b, c, d) {
        t /= d / 2;
        if (t < 1) return c / 2 * t * t + b;
        t--;
        return -c / 2 * (t * (t - 2) - 1) + b;
    }

    requestAnimationFrame(animationScroll);
}

function selectMenu(secao) {
   if(secao === 'Shop'){
   const menuSection = document.querySelector('.shop');
   smoothScroll(menuSection, 500);
   }
   if(secao === 'faq'){
   const menuSection = document.querySelector('.faq');
   smoothScroll(menuSection, 500);
   }
   if(secao === 'contact'){
   const menuSection = document.querySelector('.contact');
   smoothScroll(menuSection, 500);
   }
  
}


document.addEventListener("DOMContentLoaded", () => {
  fetch("/api/produtos")
    .then((response) => response.json())
    .then((produtos) => {
      const carousel = document.querySelector(".carouselshop");
      carousel.innerHTML = ""; // Limpa o conteúdo inicial

      produtos.forEach((produto) => {
        const item = document.createElement("div");
        item.className = "carouselshop-item";
        item.innerHTML = `
          <img src="images/teste.png" alt="${produto.nomeProduto}">
          <h4>${produto.nomeProduto}</h4>
          <p>R$ ${produto.preco.toFixed(2)}</p>
          <div class="quantity">
            <button onclick="updateQuantity(this, -1, ${produto.id}, '${produto.nomeProduto}', ${produto.preco})">-</button>
            <span>0</span>
            <button onclick="updateQuantity(this, 1, ${produto.id}, '${produto.nomeProduto}', ${produto.preco})">+</button>
          </div>
        `;
        carousel.appendChild(item);
      });
    })
    .catch((error) => {
      console.error("Erro ao carregar produtos:", error);
    });
});


// Array para armazenar itens do carrinho
let carrinho = [];

// Atualiza a quantidade do produto no carrinho
function updateQuantity(button, delta, produtoId, nomeProduto, preco) {
  let quantidadeSpan = button.parentElement.querySelector("span");
  let quantidadeAtual = parseInt(quantidadeSpan.textContent);
  let novaQuantidade = quantidadeAtual + delta;

  if (novaQuantidade < 0) return; // Não permite quantidade negativa
  quantidadeSpan.textContent = novaQuantidade;

  // Atualiza o carrinho
  let itemIndex = carrinho.findIndex((item) => item.id === produtoId);
  if (itemIndex === -1 && novaQuantidade > 0) {
    // Adiciona novo item ao carrinho
    carrinho.push({ id: produtoId, nome: nomeProduto, preco: preco, quantidade: novaQuantidade });
  } else if (itemIndex !== -1) {
    if (novaQuantidade === 0) {
      // Remove item do carrinho
      carrinho.splice(itemIndex, 1);
    } else {
      // Atualiza a quantidade
      carrinho[itemIndex].quantidade = novaQuantidade;
    }
  }

  atualizarCarrinhoModal();
}

// Atualiza o conteúdo do modal do carrinho
function atualizarCarrinhoModal() {
  let carrinhoLista = document.querySelector(".modal-body .list-group");
  carrinhoLista.innerHTML = ""; // Limpa o carrinho no modal

  carrinho.forEach((item) => {
    let li = document.createElement("li");
    li.className = "list-group-item";
    li.textContent = `${item.nome} - R$ ${item.preco.toFixed(2)} x ${item.quantidade}`;
    carrinhoLista.appendChild(li);
  });

  if (carrinho.length === 0) {
    carrinhoLista.innerHTML = "<li class='list-group-item'>Carrinho vazio</li>";
  }
}


////////////////////////
async function finalizarCompra() {
  if (carrinho.length === 0) {
    alert("Seu carrinho está vazio!");
    return;
  }
  
 
const date = new Date().toLocaleDateString('en-CA'); // "YYYY-MM-DD"
console.log(date);
 
 

  const pedido = {
    nomeComprador: "Cliente Teste", // Pegue de um input real se possível
    date: date, // Captura o momento atual no formato ISO
    endereco: "Endereço Exemplo", // Pegue de um input real se possível
    produtos: carrinho.map((item) => ({
      id: item.id,
      quantidade: item.quantidade
    }))
  };

   console.log(JSON.stringify(pedido, null, 2));


  try {
    const response = await fetch("http://localhost:8081/api/pedidos", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(pedido)
    });

    if (response.ok) {
      alert("Compra realizada com sucesso!");
      carrinho = []; // Limpa o carrinho
      atualizarCarrinhoModal();
    } else {
      alert("Erro ao finalizar a compra. Tente novamente.");
      throw new Error(`Erro HTTP: ${response.status}`);
    }
  } catch (error) {
    console.error("Erro na requisição:", error);
    alert("Erro ao finalizar a compra. Tente novamente.");
  }
}

