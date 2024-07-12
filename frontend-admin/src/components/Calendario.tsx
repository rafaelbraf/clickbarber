import moment from "moment";
import React, { useState } from "react";
import { Calendar, momentLocalizer, Views, View } from "react-big-calendar";
import { Container } from "react-bootstrap";
import "../css/Calendar.css";
import 'react-big-calendar/lib/css/react-big-calendar.css';

moment.locale('pt-br');
const localizer = momentLocalizer(moment);

export interface AgendamentoCalendario {
    id: string;
    title: string;
    start: Date;
    end: Date;
    extendedProps: {
        idExternoAgendamento: string;
    };
}

interface CalendarioProps {
    agendamentos: AgendamentoCalendario[];
    onAgendamentoClick: (idExternoAgendamento: string) => void;
}

const Calendario: React.FC<CalendarioProps> = ({ agendamentos, onAgendamentoClick }) => {
    const [view, setView] = useState<View>(Views.DAY);

    const handleSelectEvent = (event: AgendamentoCalendario) => {
        onAgendamentoClick(event.extendedProps.idExternoAgendamento);
    };

    return (
        <Container>
            <Calendar
                localizer={localizer}
                events={agendamentos}
                startAccessor="start"
                endAccessor="end"
                style={{ height: '90vh' }}
                view={view}
                views={[Views.MONTH, Views.WEEK, Views.DAY, Views.AGENDA]}
                onView={setView}
                showAllEvents={true}
                onSelectEvent={handleSelectEvent}
            />
        </Container>
    );
}

export default Calendario;