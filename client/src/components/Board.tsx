import React, { useRef, useEffect } from 'react';

const Board = () => {
    const canvasRef = useRef<HTMLCanvasElement>(null)
    const colorsRef = useRef<HTMLDivElement>(null)
    const sliderRef = useRef<HTMLInputElement>(null)

    useEffect(() => {
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

        const drawLine = (x0: number, y0: number, x1: number, y1: number, color: string, emit: boolean) => {
            context?.beginPath();
            context?.moveTo(x0, y0);
            context?.lineTo(x1, y1);
            context?.stroke();
            context?.closePath();
            if (context) context.strokeStyle = color;
            if (context && sliderRef.current) context.lineWidth = parseInt(sliderRef.current?.value);
            const w = canvas?.width;
            const h = canvas?.height;

            if (!emit) { return; }
        };

        const onMouseDown = (e: MouseEvent) => {
            drawing = true;
            current.x = e.pageX - (canvas?.offsetLeft ?? 0)
            current.y = e.pageY - (canvas?.offsetTop ?? 0)
        };

        const onMouseMove = (e: MouseEvent) => {
            if (!drawing) { return }
            drawLine(current.x, current.y, e.pageX - (canvas?.offsetLeft ?? 0), e.pageY - (canvas?.offsetTop ?? 0), current.color, true)
            current.x = e.pageX - (canvas?.offsetLeft ?? 0)
            current.y = e.pageY - (canvas?.offsetTop ?? 0)
        };

        const onMouseUp = (e: MouseEvent) => {
            if (!drawing) { return; }
            drawing = false;
            drawLine(current.x, current.y, e.pageX - (canvas?.offsetLeft ?? 0), e.pageY - (canvas?.offsetTop ?? 0), current.color, true);
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

        // -------------- make the canvas fill its parent component -----------------

        if (canvas) canvas.width = 800;
        if (canvas) canvas.height = 600;

        // const onDrawingEvent = (data) => {
        //     const w = canvas?.width;
        //     const h = canvas?.height;
        //     drawLine(data.x0 * w, data.y0 * h, data.x1 * w, data.y1 * h, data.color, true);
        // }
    })


    return (
        <div>
            <canvas ref={canvasRef} className="whiteboard" />

            <div className="canvas-controls">

                <div ref={colorsRef} className="colors">
                    <div className="color black" />
                    <div className="color red" />
                    <div className="color green" />
                    <div className="color blue" />
                    <div className="color yellow" />
                </div>
                <div className="slider-div">
                    <label>Width</label><br />
                    <input ref={sliderRef} className="slider" type="range" min="1" max="10" />
                </div>
            </div>
        </div>
    )
}

export default Board
