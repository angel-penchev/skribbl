import React, { useState } from 'react'

const Chat: React.FC = () => {
    const [messages, setMessages] = useState([])

    return (
        <div id="chat container">
            <p>a</p>
            {/* {messages.map((message, idx) => (
                <div
                    key={idx}
                    data-testid="chat-message"
                    className={`msg-${message.type}`}
                >
                    {!message.username && message.msg}
                    {message.username && (
                        <>
                            <b>{message.username}:</b> {message.msg}
                        </>
                    )}
                </div>
            ))} */}
        </div>
    )
}

export default Chat
