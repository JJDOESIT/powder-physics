package it.jjdoes.Atomix.Components.Entity.States;

import it.jjdoes.Atomix.Components.Entity.IEntityComponent;

public class Grounded implements IEntityComponent {
    private boolean _isGrounded;
    private int _rank;
    private int _length;

    public Grounded() {
        _rank = -1;
        _isGrounded = true;
    }

    public void SetIsGrounded(boolean isGrounded) {
        _isGrounded = isGrounded;
    }

    public boolean IsGrounded() {
        return _isGrounded;
    }

    public void SetRank(int rank) {
        _rank = rank;
    }

    public int GetRank() {
        return _rank;
    }

    public void SetLength(int length) {
        _length = length;
    }

    public int GetLength() {
        return _length;
    }

    public IEntityComponent Copy() {
        return new Grounded();
    }
}
