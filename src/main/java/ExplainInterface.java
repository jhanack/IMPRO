public interface ExplainInterface {
    public String getExplainOutput();
    public String preprocessExplainString(String originalExplainString);
    public String processExplainNodes(String originalExplainString);
    public String explainToIR(String originalExplainString);
}