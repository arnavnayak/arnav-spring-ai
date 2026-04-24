package arnav.springai.springaitool.service;

import arnav.springai.springaitool.model.TicketRequest;
import arnav.springai.springaitool.entity.HelpDeskTicket;
import arnav.springai.springaitool.repository.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskToolService {

    private final ChatClient chatClient;

    private final HelpDeskTicketRepository helpDeskTicketRepository;

    @Value("classpath:/promptTemplates/helpDeskSystemPromptTemplate.st")
    private Resource helpDeskSystemPromptTemplate;

    public HelpDeskTicket createTicket(TicketRequest ticketRequest, String username) {
        HelpDeskTicket helpDeskTicket = HelpDeskTicket.builder()
                .issue(ticketRequest.issue())
                .status("OPEN")
                .username(username)
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(7))
                .build();
        return helpDeskTicketRepository.save(helpDeskTicket);
    }

    public List<HelpDeskTicket> getTicketsByUsername(String username) {
        return helpDeskTicketRepository.findByUsername(username);
    }


}
