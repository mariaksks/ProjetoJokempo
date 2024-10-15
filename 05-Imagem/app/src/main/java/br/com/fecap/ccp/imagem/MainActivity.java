package br.com.fecap.ccp.imagem;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int contagemJogador = 0;
    private int contagemApp = 0;
    private int vitoriasNecessaria = 2;
    private boolean modoMelhorDe3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Definir o modo de jogo baseado na seleção do RadioButton
        RadioGroup radioGroupModo = findViewById(R.id.radioGrupoModo);
        radioGroupModo.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.radioMelhorDe3){
                modoMelhorDe3 = true;
                resetarJogo();
            } else if (i == R.id.radioPontuacaoCorrida){
                modoMelhorDe3 = false;
                resetarJogo();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void selectPapel(View view){
        this.opcaoSelecionada("papel");
    }
    public void selectPedra(View view){
        this.opcaoSelecionada("pedra");
    }
    public void selectTesoura(View view){
        this.opcaoSelecionada("tesoura");
    }
    public void opcaoSelecionada(String opcaoSelecionada){


        // Instanciamento dos Objetos ImagemView e TextView
        ImageView imagemResultado = findViewById(R.id.imagePadrao);
        TextView textResultado = findViewById(R.id.textResultado);
        TextView textContagem =findViewById(R.id.textContagem);



        // Logica de escolha das Opção da Máquina (PC):
        int numero = new Random().nextInt(3);
        String[] opcoes = {"pedra", "papel", "tesoura"};
        String opcaoApp = opcoes[numero];

        // Apresentando resultado (imagem) escolhido pela máquina (PC):
        switch (opcaoApp){
            case "pedra":
                imagemResultado.setImageResource(R.drawable.pedra);
                break;
            case "papel":
                imagemResultado.setImageResource(R.drawable.papel);
                break;
            case "tesoura":
                imagemResultado.setImageResource(R.drawable.tesoura);
                break;
        }

        // Logica do Jogo J Ken P - Definir quem é o vencedor
        if (
                (opcaoApp == "tesoura" && opcaoSelecionada == "papel") ||
                (opcaoApp == "papel" && opcaoSelecionada == "pedra") ||
                (opcaoApp == "pedra" && opcaoSelecionada == "tesoura")
        ){
            contagemApp++;

            textResultado.setText(R.string.appJogoGameOver);
        } else if (
                (opcaoSelecionada == "tesoura" && opcaoApp == "papel") ||
                (opcaoSelecionada == "papel" && opcaoApp == "pedra") ||
                (opcaoSelecionada == "pedra" && opcaoApp == "tesoura")
        ){
            contagemJogador++;

            textResultado.setText(R.string.appJogoWin);
        } else {
            textResultado.setText(R.string.appJogoEmpate);
        }
        // Atualizar a contagem
        atualizarContagem();
        //textContagem.setText(String.valueOf(textContagem));

        // verificar se alguém venceuno melhor de 3
        if(modoMelhorDe3 &&(contagemJogador == vitoriasNecessaria || contagemApp == vitoriasNecessaria)){
             if (contagemJogador == vitoriasNecessaria){
               textResultado.setText("Você venceu a melhor de 3!");
            } else{
                 textResultado.setText("Você perdeu a melhor de 3!");
             }
             resetarJogo();
        }
    }
  private void  atualizarContagem(){
        TextView textContagem = findViewById(R.id.textContagem);
      textContagem.setText("Vitórias: Jogador " + contagemJogador + " \nApp " + contagemApp);
  }
  private  void resetarJogo(){
        contagemJogador =0;
        contagemApp = 0;
      atualizarContagem();
  }

}