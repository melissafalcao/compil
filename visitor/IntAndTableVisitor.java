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

public class IntAndTableVisitor implements IVisitor<IntAndTable> {
	private Table t;
	
	public IntAndTableVisitor(Table t) {
		this.t = t;
	}

	@Override
	public IntAndTable visit(Stm s) {
		return null;
	}

	@Override
	public IntAndTable visit(AssignStm s) {
		return null;
	}

	@Override
	public IntAndTable visit(CompoundStm s) {
		return null;
	}

	@Override
	public IntAndTable visit(PrintStm s) {
		return null;
	}

	@Override
	public IntAndTable visit(Exp e) {
		return e.accept(this);
	}

	@Override
	public IntAndTable visit(EseqExp e) {
		Table table = new Interpreter(this.t).visit(e.getStm());
		this.t = table;
		return e.getExp().accept(this);
	}
	
	public double searchId(Table t, String id) {
		if(id.equals(t.id)) {
			return t.value;
		}else {
			return searchId(t.tail, id);
		}
	}
	
	@Override
	public IntAndTable visit(IdExp e) {
		return new IntAndTable (searchId(this.t, e.getId()),this.t);
	}

	@Override
	public IntAndTable visit(NumExp e) {
		return new IntAndTable(e.getNum(), this.t);
	}
	
	@Override
	public IntAndTable visit(OpExp e) {
		IntAndTable left = e.getLeft().accept(this);
		IntAndTable right = e.getRight().accept(this);
		double result = 0;

		switch (e.getOper()) {
		case OpExp.Plus:
			result = left.result + right.result;
			break;
		case OpExp.Minus:
			result = left.result - right.result;
			break;
		case OpExp.Times:
			result = left.result * right.result;
			break;
		case OpExp.Div:
			result = left.result / right.result;
			break;
		}

		return new IntAndTable(result, right.table);
	}

	@Override
	public IntAndTable visit(ExpList el) {
		return null;
	}

	@Override
	public IntAndTable visit(PairExpList el) {
		return null;
	}

	@Override
	public IntAndTable visit(LastExpList el) {
		return null;
	}


}	