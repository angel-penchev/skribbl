import React, { useState } from 'react'
import SockJsClient from 'react-stomp'
import { RouteComponentProps, useHistory } from 'react-router-dom'
import Board from '../components/Board'
import Chat from '../components/Chat'
import WordSelectPopup from '../components/WordSelectPopup'

const Room: React.FC<RouteComponentProps> = ({location}) => {
    const [socketClient, setSocketClient] = useState()
    const [isBoardUnlocked, setIsBoardUnlocked] = useState(false)
    const [isWordDialogOpen, setIsWordDialogOpen] = useState(false);
    const [wordsInDialog, setWordsInDialog] = useState<string[]>([])
    const roomId = location.pathname.split("/").pop()

    // @ts-ignore
    const username = location.state ? location.state.username : prompt("Please, enter a username", "Gosho");
    const handleClose = (id: number) => {
        setIsWordDialogOpen(false);
        // @ts-ignore
        if (socketClient) socketClient.sendMessage(`/app/room/${roomId}/game`, JSON.stringify({
            type: 'word-selected',
            message: wordsInDialog[id]
        }));
    }
    const history = useHistory();

    return (
        <div>
            <div className="container">
                <span><b>Room ID: </b></span>
                <span>{roomId}</span>
            </div>
            <div className="container container-wide">
                <Board roomId={roomId ?? ''} isUnlocked={isBoardUnlocked} />
                <Chat roomId={roomId ?? ''} username={username} />
            </div>

            <WordSelectPopup isDialogOpen={isWordDialogOpen} wordlist={wordsInDialog}  handleClose={handleClose} />
            
            <SockJsClient url='http://localhost:8080/skribbl/'
                topics={[`/topic/room/${roomId}/game`]}
                onConnect={() => {
                    // @ts-ignore
                    if (socketClient) socketClient.sendMessage(`/app/room/${roomId}/game`, JSON.stringify({
                        type: 'connection',
                        message: username
                    }));
                }}
                onDisconnect={() => {
                    history.push('/');
                }}
                onMessage={(message: any) => {
                    console.log(message)
                    switch (message.type) {
                        case 'word-select-prompt':
                            if (message.message === username) {
                                setWordsInDialog(message.wordlist);
                                setIsWordDialogOpen(true);
                            }
                            break;
                        case 'round-start':
                            if (message.message === username) {
                                setIsBoardUnlocked(true);
                            }
                            break;
                    }
                }}
                ref={(socketClient: any) => {
                    setSocketClient(socketClient)
                }} />
        </div>
    )
}

export default Room
