 public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getY() != Math.abs(destination.getY()-2)&&source.getX()!=Math.abs(destination.getY()-1))

                    return false;


        else if (source.getY() != Math.abs(destination.getY()-1)&&source.getX()!=Math.abs(destination.getX()-2)) {
            return false;
        } else {
            return true;
        }

    }
