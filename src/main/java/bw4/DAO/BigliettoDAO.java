public class BigliettoDAO {
    private final EntityManager em;

    public BigliettoDAO(EntityManager em) { this.em = em; }

    public void obliteraBiglietto(UUID bigliettoId, Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Biglietto biglietto = em.find(Biglietto.class, bigliettoId);
            if (biglietto == null) {
                System.out.println("Biglietto non trovato!");
                return;
            }
            if (biglietto.isObliterato()) {
                System.out.println("Biglietto già obliterato!");
                return;
            }
            biglietto.setObliterato(true);
            biglietto.setMezzo(mezzo);
            biglietto.setDataEOra(LocalDateTime.now());
            em.merge(biglietto);
            transaction.commit();
            System.out.println("Biglietto obliterato!");
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            System.err.println("Errore: " + e.getMessage());
        }
    }

    public long countObliterazioniPerMezzo(UUID mezzoId, LocalDateTime da, LocalDateTime a) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Biglietto b " +
                                "WHERE b.obliterato = true " +
                                "AND b.mezzo.idMezzo = :mezzoId " +
                                "AND b.dataEOra BETWEEN :da AND :a", Long.class)
                .setParameter("mezzoId", mezzoId)
                .setParameter("da", da)
                .setParameter("a", a)
                .getSingleResult();
    }
}
