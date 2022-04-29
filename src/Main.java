import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Alberto Gutiérrez Morán
 */

public class Main {
    public static int MOD = 0;

    public static void main(String[] args) throws FileNotFoundException {

        //CIFRAMOS EL ALFABETO
        String alf = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZáéíóúÁÉÍÓÚ0123456789 ,.:!-¿?()";
        ArrayList<Alfabeto> alfabeto = new ArrayList<>();
        MOD = alf.length();
        for(int i=0; i<alf.length(); i++){
            Alfabeto nuevo = new Alfabeto(alf.charAt(i), i);
            alfabeto.add(nuevo);
        }

        //OBTENEMOS EL MENSAJE CIFRADO
        String msgCifrado = "9MúqR2SXkÑRN2G?E Éfáv.úc7ú:(ÁÉ,ñ:c1ñKtÓjW2-vgrJQKMX!ÑíCIVa?!wiuf6wÓézuPC:í7kNfQLÉóI.é2Nb?réd?dGJkíSÚMñsózWMí,:-" +
                "ñ82Q8IFíiKcCwKnúJ7bspvSn5YqñXomÓWbñe4ÍAfad3iám3a¿rLáG7)ÑGBXF:mbsTuÑ0)vWL " +
                "ÁlgBmXRú4Jgó.G:hoéZ8SÁ?ñ5qTW8óh¿ÁILEAN,ÓZE7P(CH)2oZemVGiTQUABuBUñK6";

        //GENERAMOS LA CLAVE EXTENDIDA
        String Kstring = "IMMANUEL KANT";
        int k[] = getClaveExtendida(alfabeto,Kstring,msgCifrado.length());

        //OBTENEMOS EL MENSAJE EN CLARO
        String msgClaro = getMensajeClaro(alfabeto, k, msgCifrado);
        System.out.println(msgClaro);
    }

    private static String getMensajeClaro(ArrayList<Alfabeto> list, int k[], String msgCifrado){
        int msgCif[] = new int[msgCifrado.length()];
        int msgCla[] = new int[msgCifrado.length()];
        String msgClaro = "";

        for(int i=0; i<msgCifrado.length(); i++){
            msgCif[i] = getPos(msgCifrado.charAt(i),list);
            msgCla[i] = modulo((msgCif[i]-k[i]),MOD);
            char add = getChar(msgCla[i],list);
            if(add==' ' && msgCla[i]==msgCla[i-1]) add='\n';    //AÑADIMOS SALTO DE LÍNEA SI HAY DOS ESPACIOS SEGUIDOS
            msgClaro+=add;
        }

        return msgClaro;
    }

    private static int[] getClaveExtendida(ArrayList<Alfabeto> alfabeto, String Kstring, int len){
        int k[] = new int[len];
        for(int i=0; i<Kstring.length(); i++){
            k[i] = getPos(Kstring.charAt(i),alfabeto);
        }

        int funcion[] = k;
        for(int i=Kstring.length(); i<len; i++){
            int valor = 0;
            for(int x=0; x<Kstring.length(); x++){
                valor+=funcion[x]*k[i-(x+1)]; //k=[1,4] => valor=1*[k-1] + 4*[k-2]
            }
            k[i] = modulo(valor,MOD);
        }

        return k;
    }

    private static int getPos(char c, ArrayList<Alfabeto> list){
        for(int i=0; i<list.size(); i++){
            if(c==list.get(i).getChar()){
                return list.get(i).getPos();
            }
        }
        return -1;
    }

    private static char getChar(int pos, ArrayList<Alfabeto> list){
        for(int i=0; i<list.size(); i++){
            if(pos==list.get(i).getPos()){
                return list.get(i).getChar();
            }
        }
        return ' ';
    }

    private static int modulo(int x, int mod){
        if(x<0){
            x=-1*x;
            return mod-(x-(mod * (x/mod)));
        }
        return x%mod;
    }

}
