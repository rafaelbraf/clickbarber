import moment from "moment";
import React, { useState } from "react";
import { Calendar, Event, momentLocalizer, Views, View } from "react-big-calendar";
import { Container } from "react-bootstrap";
import "../css/Calendar.css";
import 'react-big-calendar/lib/css/react-big-calendar.css';

moment.locale('America/Sao_Paulo');
const localizer = momentLocalizer(moment);

export interface AgendamentoCalendario {
    id: string;
    title: string;
    start: Date;
    end: Date;
}

interface CalendarioProps {
    agendamentos: AgendamentoCalendario[];
}

const Calendario: React.FC<CalendarioProps> = ({ agendamentos }) => {
    const [view, setView] = useState<View>(Views.DAY);

    const eventos: Event[] = agendamentos.map(agendamento => ({
        id: agendamento.id,
        title: agendamento.title,
        start: agendamento.start,
        end: agendamento.end
    }));

    return (
        <Container>
            <Calendar
                localizer={localizer}
                events={eventos}
                startAccessor="start"
                endAccessor="end"
                style={{ height: '90vh' }}
                view={view}
                views={[Views.MONTH, Views.WEEK, Views.DAY, Views.AGENDA]}
                onView={setView}
                showAllEvents={true}
            />
        </Container>
    );
}

export default Calendario;