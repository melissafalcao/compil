package br.ufpe.cin.if688.jflex;

%%

/*
A linha atual pode ser acessada por yyline
e a coluna atual com yycolumn.
*/
%line
%column

/* Se quisermos 'interfacear' com um parser gerado pelo sistema CUP
%cup
*/

//encoding
%unicode

// faz com que a classe tenha uma função main e torna possivel que a classe gerada seja usada como reconhecedor
%standalone

//nomeia a classe
%class MiniJava

/*
Declarações

código dentro de %{ e %}, é copiado para a classe gerada.
a ideia é utilizar este recurso para declarar atributos e métodos usados nas ações
*/
%{
int qtdeID=0;
%}


%eof{
//System.out.println("Quantidade de Identificadores encontrados: "+qtdeID);
%eof}

/*-*
 * PADROES NOMEADOS:
 */
letter          = [A-Za-z] | "_"
digit           = [0-9]
digit2          = [1-9]
integer         = {digit}+
alphanumeric    = {letter}|{digit}
identifier      = ({letter} | [])({alphanumeric} | [])*
palavrasReserv  = "boolean"  | "class" | "public" | "extends" | "static" | "void" | "main" | "String" | "int" | "while" | "if" | "else" | "return" | "length" | "true" | "false" | "this" | "new" | "System.out.println"
operadores      = "&&" | "<" | "==" | "!=" | "+" | "-" | "*" | "!"
delimitadores   = ";" | "." | "," | "=" | "(" | "{" |"[" | ")" | "}" |"]"
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

/* comments */
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+"/"
CommentContent   = ( [^*] | \*+ [^/*] )*




%%
/**
 * REGRAS LEXICAS:
 */

{palavrasReserv} {System.out.println("token gerado foi um reservado: '"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn);}
{integer} {System.out.println("token gerado foi um integer: '"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn);}
{operadores} {System.out.println("token gerado foi um operador: '"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn);}
{delimitadores} {System.out.println("token gerado foi um delimitador: '"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn);}
{identifier} { qtdeID++; System.out.println("token gerado foi um id: '"+yytext()+"' na linha: "+yyline+", coluna: "+yycolumn);}
{Comment} {}
{WhiteSpace} {}
.               { System.out.println("Illegal char, '" + yytext() +
                    "' line: " + yyline + ", column: " + yycolumn); }