import React, { useRef, useState } from 'react';
import SockJsClient from 'react-stomp';

interface Props {
    roomId: string;
    isUnlocked: boolean;
}

interface Line {
    startX: number;
    startY: number;
    endX: number;
    endY: number;
    strokeWidth: number;
    color: string;
    emit: boolean;
}

const Board: React.FC<Props> = ({ roomId, isUnlocked: isLocked }) => {
    const canvasRef = useRef<HTMLCanvasElement>(null)
    const colorsRef = useRef<HTMLDivElement>(null)
    const sliderRef = useRef<HTMLInputElement>(null)
    const [socketClient, setSocketClient] = useState()

    const canvas = canvasRef.current
    const context = canvas?.getContext('2d')

    const colors = document.getElementsByClassName('color')

    const current = {
        x: 0,
        y: 0,
        color: 'black',
    };

    const onColorUpdate = (e: any) => {
        current.color = e?.target?.className.split(' ')[1];
    };

    for (let i = 0; i < colors.length; i++) {
        colors[i].addEventListener('click', onColorUpdate, false);
    }

    let drawing = false;

    const drawLine = (line: Line) => {
        context?.beginPath();
        context?.moveTo(line.startX, line.startY);
        context?.lineTo(line.endX, line.endY);
        context?.stroke();
        context?.closePath();
        if (context) context.strokeStyle = line.color;
        if (context) context.lineWidth = line.strokeWidth;

        if (!line.emit) { return; }

        // @ts-ignore
        if (socketClient) socketClient.sendMessage(`/app/room/${roomId}/board`, JSON.stringify(
            (({ emit, ...o }) => o)(line)
        ));

    };

    const onMouseDown = (e: MouseEvent) => {
        if (!isLocked) { return; }
        drawing = true;
        current.x = e.pageX - (canvas?.offsetLeft ?? 0)
        current.y = e.pageY - (canvas?.offsetTop ?? 0)
    };

    const onMouseMove = (e: MouseEvent) => {
        if (!isLocked) { return; }
        if (!drawing) { return; }
        drawLine({
            startX: current.x,
            startY: current.y,
            endX: e.pageX - (canvas?.offsetLeft ?? 0),
            endY: e.pageY - (canvas?.offsetTop ?? 0),
            color: current.color,
            strokeWidth: parseInt(sliderRef.current?.value ?? '1'),
            emit: true
        });
        current.x = e.pageX - (canvas?.offsetLeft ?? 0)
        current.y = e.pageY - (canvas?.offsetTop ?? 0)
    };

    const onMouseUp = (e: MouseEvent) => {
        if (!isLocked) { return; }
        if (!drawing) { return; }
        drawing = false;
        drawLine({
            startX: current.x,
            startY: current.y,
            endX: e.pageX - (canvas?.offsetLeft ?? 0),
            endY: e.pageY - (canvas?.offsetTop ?? 0),
            color: current.color,
            strokeWidth: parseInt(sliderRef.current?.value ?? '1'),
            emit: true
        });
    };

    const throttle = (callback: Function, delay: number) => {
        let previousCall = new Date().getTime();
        return function () {
            const time = new Date().getTime();

            if ((time - previousCall) >= delay) {
                previousCall = time;
                callback.apply(null, arguments);
            }
        };
    };

    canvas?.addEventListener('mousedown', onMouseDown, false);
    canvas?.addEventListener('mouseup', onMouseUp, false);
    canvas?.addEventListener('mouseout', onMouseUp, false);
    canvas?.addEventListener('mousemove', throttle(onMouseMove, 10), false);

    if (canvas) canvas.width = 800;
    if (canvas) canvas.height = 600;

    return (
        <div className="whiteboard">
            <canvas ref={canvasRef} />

            <div className="canvas-controls">

                <div ref={colorsRef} className="colors">
                    <div className="color black" />
                    <div className="color red" />
                    <div className="color green" />
                    <div className="color blue" />
                    <div className="color yellow" />
                    <div className="color white" />
                </div>
                <div className="slider-div">
                    <label>Width</label><br />
                    <input ref={sliderRef} className="slider" type="range" min="1" max="10" />
                </div>
            </div>

            <SockJsClient url='http://localhost:8080/skribbl/'
                topics={[`/topic/room/${roomId}/board`]}
                onMessage={(msg: Line) => {
                    drawLine({ ...msg, emit: false });
                }}
                ref={(socketClient: any) => {
                    setSocketClient(socketClient)
                }} />
        </div>
    )
}

export default Board
