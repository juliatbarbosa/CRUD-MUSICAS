var modalCadastro = new bootstrap.Modal(document.getElementById('cadastroMusica'));
var modalExcluir = new bootstrap.Modal(document.getElementById('excluirCadastro'));
var modalToast = bootstrap.Toast.getOrCreateInstance(document.getElementById('toast'))
var loading = document.querySelector('#loading')

function init() {
	document.querySelector('#cancelarCadastro').onclick = function() {
		modalCadastro.hide()
	}

	document.querySelector('#naoExcluir').onclick = function() {
		modalExcluir.hide()
	}

	document.querySelector('#imgPesquisa').onclick = function() {
		pesquisar()
	};

	document.querySelector('#inputPesquisar').addEventListener('keydown', function(event) {
		if (event.key === 'Enter') {
			document.querySelector('#inputPesquisar').blur()

			if (document.querySelector('#imgPesquisa').src.includes('close.png')) {
				getMusicas(document.querySelector('#inputPesquisar').value.trim())
			} else {
				pesquisar();

			}
		}
	});

	getMusicas(null)
}

// ################### UTILS ###################

function abrirLoading() {
	loading.style.display = "flex"
}

function fecharLoading() {
	loading.style.display = "none"
}

function toast(mensagem, success) {
	document.querySelector('.toast-body').innerHTML = mensagem
	document.querySelector('.toast').style.backgroundColor = (success ? 'var(--primaria-700)' : 'var(--error)');
	modalToast.show()
}

function validarInputs(...inputs) {
	let temErro = false;

	inputs.forEach(input => {
		if (input.value.trim() === "") {
			input.classList.add('inputError');
			temErro = true;
		} else {
			input.classList.remove('inputError');
		}
	});

	return !temErro;
}

// ################### SERVIDOR ###################

function getMusicas(nomemusica) {
	abrirLoading()
	var endereco = "../MusicaAPI/?nome=" + (nomemusica == undefined ? "" : nomemusica)
	fetch(endereco)
		.then(resp => resp.json())
		.then(dados => listarMusicas(dados))
		.catch(err => console.error("Erro ao buscar dados:", err))
		.finally(function() {

			fecharLoading()
		});

}

function getMusica(id) {
	abrirLoading()
	fetch("../MusicaAPI/" + id)
		.then(resp => resp.json())
		.then(dados => editarCadastro(dados))
		.catch(err => console.error("Erro ao buscar dados:", err))
		.finally(function() {
			fecharLoading()
		});
}

function deleteMusicas(id) {
	abrirLoading()
	fetch("../MusicaAPI/" + id, { method: "DELETE" })
		.then(resp => resp.json())
		.then(function(retorno) {
			toast(retorno.message, retorno.success);
		}).finally(function() {
			getMusicas(null);
		});
}

function postPutMusicas(musica, id) {
	abrirLoading()
	let body = JSON.stringify(musica);
	let endereco = "../MusicaAPI";
	let metodo = "POST";
	if (id) {
		endereco = "../MusicaAPI/" + id;
		metodo = "PUT";
	}
	fetch(endereco, { method: metodo, body: body })
		.then(resp => resp.json())
		.then(function(retorno) {
			toast(retorno.message, retorno.success);
		})
		.catch(err => console.error("Erro ao buscar dados:", err))
		.finally(function() {
			modalCadastro.hide()
			getMusicas(null);
		});
}

// ################### MODAL ###################

function novoCadastro() {
	// alterar titulo
	document.querySelector('#tituloModal').innerHTML = "Nova música";
	// limpar campos
	document.querySelector('#inputnomemusica').value = '';
	document.querySelector('#inputcantor').value = '';
	document.querySelector('#inputcompositor').value = '';
	document.querySelector('#inputgenero').value = '';
	document.querySelector('#inputanolancamento').value = '';
	// remover erros
	document.querySelector('#inputnomemusica').classList.remove('inputError');
	document.querySelector('#inputcantor').classList.remove('inputError');
	document.querySelector('#inputcompositor').classList.remove('inputError');
	document.querySelector('#inputgenero').classList.remove('inputError');
	document.querySelector('#inputanolancamento').classList.remove('inputError');
	// botao salvar
	document.querySelector('#salvarCadastro').onclick = function() {
		salvar()
	}
	// abrir modal
	modalCadastro.show();
}

function editarCadastro(dados) {
	//alterar titulo
	document.querySelector('#tituloModal').innerHTML = "Alterar música";
	// preencher campos
	document.querySelector('#inputnomemusica').value = dados.nomemusica;
	document.querySelector('#inputcantor').value = dados.cantor;
	document.querySelector('#inputcompositor').value = dados.compositor;
	document.querySelector('#inputgenero').value = dados.genero;
	document.querySelector('#inputanolancamento').value = dados.anolancamento;
	// remover erros
	document.querySelector('#inputnomemusica').classList.remove('inputError');
	document.querySelector('#inputcantor').classList.remove('inputError');
	document.querySelector('#inputcompositor').classList.remove('inputError');
	document.querySelector('#inputgenero').classList.remove('inputError');
	document.querySelector('#inputanolancamento').classList.remove('inputError');
	// botao salvar
	document.querySelector('#salvarCadastro').onclick = function() {
		salvar(dados.id)
	}
	// abrir modal
	modalCadastro.show();
}

function excluir(id) {
	modalExcluir.show()
	document.querySelector('#simExcluir').onclick = function() {
		deleteMusicas(id)
		modalExcluir.hide()
	}
}

function salvar(id) {
	const inputnomemusica = document.querySelector('#inputnomemusica');
	const inputcantor = document.querySelector('#inputcantor');
	const inputcompositor = document.querySelector('#inputcompositor');
	const inputgenero = document.querySelector('#inputgenero');
	const inputanolancamento = document.querySelector('#inputanolancamento');

	let musica = {};
	musica.nomemusica = inputnomemusica.value.trim();
	musica.cantor = inputcantor.value.trim();
	musica.compositor = inputcompositor.value.trim();
	musica.genero = inputgenero.value.trim();
	musica.anolancamento = inputanolancamento.value.trim();

	if (validarInputs(inputnomemusica, inputcantor, inputcompositor, inputgenero, inputanolancamento)) {
		postPutMusicas(musica, id)
	} else {
		toast('Preencha todos os campos!', false)
	}
}

// ################### TABELA ###################

function listarMusicas(dados) {
	if (dados.length == 0) {
		document.querySelector('.seminfo').style.display = "flex"
		document.querySelector('.cardTabela').style.display = "none"
	} else {
		document.querySelector('.seminfo').style.display = "none"
		document.querySelector('.cardTabela').style.display = ""

		var tab = '';
		for (let i in dados) {
			tab += "<tr>"
				+ "<td class='colunaCentro'>" + dados[i].id + "</td>"
				+ "<td>" + dados[i].nomemusica + "</td>"
				+ "<td>" + dados[i].cantor + "</td>"
				+ "<td>" + dados[i].compositor + "</td>"
				+ "<td>" + dados[i].genero + "</td>"
				+ "<td>" + dados[i].anolancamento + "</td>"
				+ `<td><div id='btnEdit' title="Alterar" onclick='getMusica(${dados[i].id})'><img src='img/edit.png' style='width:18px;'></div></td>`
				+ `<td><div id='btnDelete' title="Excluir" onclick='excluir(${dados[i].id})'><img src='img/delete.png' style='width:18px;'></div></td>`
				+ "</tr>";
		}

		document.querySelector("#listaMusicas").innerHTML = tab;
	}

}

function pesquisar() {
	var icone = document.querySelector('#imgPesquisa')
	var nomemusica = document.querySelector('#inputPesquisar');
	if (icone.src.includes('pesquisa.png')) {
		icone.src = 'img/close.png'
		getMusicas(nomemusica.value.trim())
	} else if (icone.src.includes('close.png')) {
		icone.src = 'img/pesquisa.png'
		nomemusica.value = ''
		getMusicas(null)
	}
}

document.addEventListener('DOMContentLoaded', init);
