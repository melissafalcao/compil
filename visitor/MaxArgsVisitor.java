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

public class MaxArgsVisitor implements IVisitor<Integer> {
	
	private static Stm ref = null;
	
	private int maxA;
	
	@Override
	public Integer visit(Stm s) {
		if(ref == null) {
			ref = s;
			maxA = 0;
		}
		
		s.accept(this);
		
		int result = maxA;
		
		if (ref == s) {
			ref = null;
			maxA = 0;
		}
		
		return result;
	}

	@Override
	public Integer visit(AssignStm s) {
		s.getExp().accept(this);
		
		return maxA;
	}

	@Override
	public Integer visit(CompoundStm s) {
		s.getStm1().accept(this);
		s.getStm2().accept(this);
		
		return maxA;
	}

	@Override
	public Integer visit(PrintStm s) {
		
		maxA = Math.max(maxA, s.getExps().accept(this));
		
		return Math.max(maxA, s.getExps().accept(this));
	}

	@Override
	public Integer visit(Exp e) {
		
		e.accept(this);
		
		return maxA;
	}

	@Override
	public Integer visit(EseqExp e) {
		e.getStm().accept(this);
		e.getExp().accept(this);
		
		return maxA;
	}

	@Override
	public Integer visit(IdExp e) {
		
		return maxA;
	}

	@Override
	public Integer visit(NumExp e) {
		return maxA;
	}

	@Override
	public Integer visit(OpExp e) {
		
		e.getLeft().accept(this);
		e.getRight().accept(this);
		
		return maxA;
	}

	@Override
	public Integer visit(ExpList el) {
		
		el.accept(this);
		
		return maxA;
	}

	@Override
	public Integer visit(PairExpList el) {
		el.getHead().accept(this);
		
		return 1 + el.getTail().accept(this);
	}

	@Override
	public Integer visit(LastExpList el) {
		//
		el.getHead().accept(this);
		
		return 1;
	}

}