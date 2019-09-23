package br.ufpe.cin.if688.visitor;

import br.ufpe.cin.if688.ast.AssignStm;
import br.ufpe.cin.if688.ast.CompoundStm;
import br.ufpe.cin.if688.ast.EseqExp;
import br.ufpe.cin.if688.ast.Exp;
import br.ufpe.cin.if688.ast.ExpList;
import br.ufpe.cin.if688.ast.IdExp;
import br.ufpe.cin.if688.ast.LastExpList;
import br.ufpe.cin.if688.ast.NumExp;
import br.ufpe.cin.if688.ast.OpExp;
import br.ufpe.cin.if688.ast.PairExpList;
import br.ufpe.cin.if688.ast.PrintStm;
import br.ufpe.cin.if688.ast.Stm;
import br.ufpe.cin.if688.symboltable.IntAndTable;
import br.ufpe.cin.if688.symboltable.Table;

public class Interpreter implements IVisitor<Table> {
	
	//a=8;b=80;a=7;
	// a->7 ==> b->80 ==> a->8 ==> NIL
	private Table t;
	
	public Interpreter(Table t) {
		this.t = t;
	}

	@Override
	public Table visit(Stm s) {
		return s.accept(this);
	}

	@Override
	public Table visit(AssignStm s) {
		
		Table tab = s.getExp().accept(this);
		
		tab.id = s.getId();
		Table aux = tab;
		this.t = tab;
		
		return aux;
	}

	@Override
	public Table visit(CompoundStm s) {
		
		s.getStm1().accept(this);
		s.getStm2().accept(this);
		
		return this.t;
	}

	@Override
	public Table visit(PrintStm s) {
		
		s.getExps().accept(this);
		
		return this.t;
	}

	@Override
	public Table visit(Exp e) {
		
		return e.accept(this);
		
		
	}

	@Override
	public Table visit(EseqExp e) {
		
		IntAndTable aux = new IntAndTableVisitor(this.t).visit(e);
		double resp = aux.result;
		this.t = aux.table;
		return new Table("mefista", resp, this.t);
	}

	@Override
	public Table visit(IdExp e) {
		IntAndTable aux = new IntAndTableVisitor(this.t).visit(e);
		double result = aux.result;

		return new Table("meli", result, this.t);
	}

	@Override
	public Table visit(NumExp e) {
		IntAndTable aux = new IntAndTableVisitor(this.t).visit(e);
		double result = aux.result;

		return new Table("mel", result, this.t);
	}

	@Override
	public Table visit(OpExp e) {
		IntAndTable aux = new IntAndTableVisitor(this.t).visit(e);
		double result = aux.result;

		return new Table("lissa", result, this.t);
	}

	@Override
	public Table visit(ExpList el) {
		return el.accept(this);
	}

	@Override
	
	public Table visit(PairExpList el) {
		double value = el.getHead().accept(this).value;
		System.out.print(value + "\n");
		el.getTail().accept(this);
		return this.t;
	}

	@Override
	public Table visit(LastExpList el) {
		Table t = el.getHead().accept(this);
		System.out.print(t.value + "\n");
		return t;
	}


}