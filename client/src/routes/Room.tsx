/* eslint-disable no-fallthrough */
import React, { useState } from 'react'
import SockJsClient from 'react-stomp'
import { RouteComponentProps, useHistory } from 'react-router-dom'
import Board from '../components/Board'
import Chat from '../components/Chat'
import WordSelectPopup from '../components/WordSelectPopup'
import ScoreboardPopup from '../components/ScoreboardPopup'

const Room: React.FC<RouteComponentProps> = ({location}) => {
    const [socketClient, setSocketClient] = useState()
    const [isBoardUnlocked, setIsBoardUnlocked] = useState(false)
    const [isWordDialogOpen, setIsWordDialogOpen] = useState(false);
    const [isScoreboardDialogOpen, setIsScoreboardDialogOpen] = useState(false)
    const [wordsInDialog, setWordsInDialog] = useState<string[]>([])
    const [scoreboard, setScoreboard] = useState<Array<any>>([])
    const roomId = location.pathname.split("/").pop()
    const [isGameOver, setIsGameOver] = useState(false)
    const [isRoundOver, setIsRoundOver] = useState(false)
    const [canvasPng, setCanvasPng] = useState("")
    const [drawingUserName, setDrawingUserName] = useState("")

    // @ts-ignore
    const username = location.state ? location.state.username : prompt("Please, enter a username", "Gosho");
    const handleWordlistClose = (id: number) => {
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
                <Board roomId={roomId ?? ''} isUnlocked={isBoardUnlocked} setCanvasPng={setCanvasPng} isRoundOver={isRoundOver} />
                <Chat roomId={roomId ?? ''} username={username} />
            </div>

            <WordSelectPopup isDialogOpen={isWordDialogOpen} wordlist={wordsInDialog}  handleClose={handleWordlistClose} />
            <ScoreboardPopup isDialogOpen={isScoreboardDialogOpen} scoreboard={scoreboard} isGameOver={isGameOver} />
            
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
                            setIsRoundOver(false);
                            setIsScoreboardDialogOpen(false);
                            setDrawingUserName(message.message);
                            if (message.message === username) {
                                setIsBoardUnlocked(true);
                            }
                            break;

                        // @ts-ignore
                        case 'game-end':
                            setIsGameOver(true);
                        case 'round-end':
                            setIsRoundOver(true);
                            setScoreboard(message.scores);
                            setIsScoreboardDialogOpen(true);
                            fetch('http://localhost:8080/api/create-room', {
                                method: 'POST',
                                headers: {
                                    'Content-type': 'application/json',
                                },
                                body: JSON.stringify({
                                    word: message.message,
                                    drawingUserName: drawingUserName,
                                    board: canvasPng
                                }),
                            })
                    }
                }}
                ref={(socketClient: any) => {
                    setSocketClient(socketClient)
                }} />
        </div>
    )
}

export default Room
