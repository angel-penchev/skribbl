import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogContent from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import React from 'react'

interface Props {
    isDialogOpen: boolean;
    scoreboard: Array<any>
    isGameOver: boolean
}

const ScoreboardPopup: React.FC<Props> = ({ isDialogOpen, scoreboard, isGameOver }) => {
    return (
        <div>
            <Dialog open={isDialogOpen} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Scoreboard</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <span>Gosho</span>
                    </DialogContentText>

                </DialogContent>
            </Dialog>
        </div>
    )
}

export default ScoreboardPopup
