package br.com.duxusdesafio.service;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * Service que possuirá as regras de negócio para o processamento dos dados
 * solicitados no desafio!
 *
 * @author carlosau
 */
@Service
public class ApiService extends ComposicaoTime{

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time daquela data, utilizando List para fazer uma lista
     * de dados, nesse caso dos integrantes e criando um ArrayList, comparando as datas para retornar a lista com os
     * nomes daquele período.
     */


    public Time timeDaData(LocalDate data, List<Time> todosOsTimes){
        List<String> integrantes = new ArrayList<>();
        for(Time time : todosOsTimes){
            if(data.isEqual(time.getData()) ||
                    (data.isAfter(time.getData()) && data.isBefore(time.getData())) ||
                    data.isEqual(time.getData())){
                for(ComposicaoTime integrante : time.getComposicaoTime()){
                    integrantes.add(integrante.getIntegrante().getNome());
                }
            }
        }
        if(integrantes.isEmpty()){
            throw new RuntimeException("Nenhum time encontrado nessa data!");
        }
        return (Time) integrantes;
    }
    /**
     * Vai retornar o integrante que tiver presente na maior quantidade de times
     * dentro do período. Utilizando HashMap para organizar os integrantes e comparando as datas
     * para que o retorno do integrante se encaixe na data seleconada
     */


    public String integranteMaisUsado(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
       Map<String, Long> contagemIntegrante = new HashMap<>();

       for(Time time : todosOsTimes){
           LocalDate data = time.getData();
           if(data.isEqual(dataInicial) || data.isEqual(dataFinal) || (data.isAfter(dataInicial) && data.isBefore(dataFinal))){
               for(ComposicaoTime integrantes : time.getComposicaoTime()){
                   if(contagemIntegrante.containsKey(integrantes)){
                       contagemIntegrante.put(String.valueOf(integrantes), contagemIntegrante.get(integrantes) + 1);
                   }else{
                       contagemIntegrante.put(String.valueOf(integrantes), 1L);
                   }
               }
           }
       }
       String integranteMaisUsado = "";
       long contagemMaior = 0;

       for(Map.Entry<String, Long> entry: contagemIntegrante.entrySet()){
           if(entry.getValue() > contagemMaior){
               integranteMaisUsado = entry.getKey();
               contagemMaior = entry.getValue();
           }
       }
        return integranteMaisUsado;
    }

    /**
     * Vai retornar uma lista com os nomes dos integrantes do time mais comum
     * dentro do período, utilizando HashMap para organizar a contagem de integrantes e ArrauList
     * para devolver a lista de nomes, usando o mesmo método de comparação de datas utilizadas
     * anteriormente
     */
    public List<String> timeMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        List<String> integrantesComuns = new ArrayList<>();
        Map<String, Integer> contadorDeIntegrantes = new HashMap<>();
        int contador = 0;

        for(Time time : todosOsTimes){
            LocalDate dataInicioTime = time.getData();
            LocalDate dataFinalTime = time.getData();
            if((dataInicioTime.isEqual(dataInicial) || dataInicioTime.isAfter(dataInicial)) &&
                    (dataFinalTime == null || dataFinalTime.isEqual(dataFinal) ||  dataFinalTime.isBefore(dataFinal))){
               for(ComposicaoTime integrantes : time.getComposicaoTime()){
                   int contar = contadorDeIntegrantes.getOrDefault(integrantes, 0 ) + 1;
                   contadorDeIntegrantes.put(String.valueOf(integrantes), contar);
                   if(contar > contador){
                       contador = contar;
                       integrantesComuns.clear();
                       integrantesComuns.add(String.valueOf(integrantes));
                   } else if (contar == contador) {
                       integrantesComuns.add(String.valueOf(integrantes));
                   }
               }

            }
        }
        return integrantesComuns;
    }

    /**
     * Vai retornar a função mais comum nos times dentro do período, utilizando HashMap para organizar
     * os obejetos dentro dele e por meio getters peganda a função mais comum
     */
    public String funcaoMaisComum(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
        Map<String, Integer> funcaoMap = new HashMap<>();
        int contadorMaior = 0;
        String funcaoComum ="";

        for(Time time : todosOsTimes){
            LocalDate dataInicialTime = time.getData();
            LocalDate dataFinalTime = time.getData();

            if((dataInicialTime.isEqual(dataInicial) || dataInicialTime.isAfter(dataInicial)) &&
                    (dataFinalTime == null || dataFinalTime.isEqual(dataFinal) ||  dataFinalTime.isBefore(dataFinal))){
                for (ComposicaoTime integrantes : time.getComposicaoTime()){
                    String funcao = getIntegrante().funcao;
                    int contador = funcaoMap.getOrDefault(funcao, 0) + 1;
                    funcaoMap.put(funcao, contador);

                    if(contador > contadorMaior){
                        contadorMaior = contador;
                        funcaoComum = funcao;
                    }
                }
            }
        }
        return funcaoComum;
    }

    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período, utilizando HashMap para
     * organizar a franquia e comparando a data inicial com a final para retornar apenas a franquia
     * que foi mais utilizada naquele período
     */
    public String franquiaMaisFamosa(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes) {
       Map<String, Long> franquiaMap= new HashMap<>();
       long contadorMaior = 0;
       String franquiaFamosa = "";

       for(Time time : todosOsTimes ){
           LocalDate dataInicialTime = time.getData();
           LocalDate dataFinalTime = time.getData();

           if((dataInicialTime.isEqual(dataInicial) || dataInicialTime.isAfter(dataInicial)) &&
                   (dataFinalTime == null || dataFinalTime.isEqual(dataFinal) ||  dataFinalTime.isBefore(dataFinal))){
               String franquia = getIntegrante().getFranquia();
               Long contador = franquiaMap.getOrDefault(franquia, 0L) + 1;
               franquiaMap.put(franquia, contador);

               if(contador > contadorMaior){
                   contadorMaior = contador;
                   franquiaFamosa = franquia;
               }
           }
       }
        return franquiaFamosa;
    }


    /**
     * Vai retornar o nome da Franquia mais comum nos times dentro do período, utilizando HashMap
     * para contar por Franquia, comparando a data final e inicial para retornar o número de franquias daquele
     * período
     */
    public Map<String, Long> contagemPorFranquia(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
       Map<String, Long> contagemPorFranquia = new HashMap<>();

       for(Time time : todosOsTimes){
           LocalDate data = time.getData();
           if(data.isEqual(dataInicial) || data.isEqual(dataFinal)  || (data.isAfter(dataInicial) && data.isBefore(dataFinal))){
               String franquia = getIntegrante().getFranquia();
               if(contagemPorFranquia.containsKey(franquia)){
                   contagemPorFranquia.put(franquia, contagemPorFranquia.get(franquia) + 1 );
               }else{
                   contagemPorFranquia.put(franquia, 1L);
               }
           }
       }


        return contagemPorFranquia;
    }

    /**
     * Vai retornar o número (quantidade) de Funções dentro do período, utilizando HashMap para contar a função
     * que aparece, comparando a data final e inicial e retornando a contagem
     */
    public Map<String, Long> contagemPorFuncao(LocalDate dataInicial, LocalDate dataFinal, List<Time> todosOsTimes){
       Map<String, Long> contagemPorFuncao = new HashMap<>();

       for(Time time : todosOsTimes){
           LocalDate data = time.getData();
           if(data.isEqual(dataInicial) || data.isEqual(dataFinal) || (data.isAfter(dataInicial) && data.isBefore(dataFinal))) {
               String funcao = getIntegrante().getFuncao();
                if(contagemPorFuncao.containsKey(funcao)){
                    contagemPorFuncao.put(funcao, contagemPorFuncao.get(funcao) + 1);
                }else {
                    contagemPorFuncao.put(funcao, 1L);
                }
           }

       }
        return contagemPorFuncao;
    }

}
