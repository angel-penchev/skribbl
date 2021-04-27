import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogContent from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import React from 'react'

interface Props {
    handleClose: (id: number) => any;
    isDialogOpen: boolean;
    wordlist: string[];
}

const WordSelectPopup: React.FC<Props> = ({ handleClose, isDialogOpen, wordlist }) => {
    return (
        <div>
            <Dialog open={isDialogOpen} onClose={handleClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title">Pick a word</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <Button onClick={() => handleClose(0)} color="primary">
                            {wordlist[0]}
                        </Button>
                        <Button onClick={() => handleClose(1)} color="primary">
                            {wordlist[1]}
                        </Button>
                        <Button onClick={() => handleClose(2)} color="primary">
                            {wordlist[2]}
                        </Button>
                    </DialogContentText>

                </DialogContent>
            </Dialog>
        </div>
    )
}

export default WordSelectPopup
